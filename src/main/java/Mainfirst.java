import java.util.*;
import java.util.List;

public class Mainfirst {
    public static void main(String[] args) {

//        List<Student> studentlist = new ArrayList<>(); // from Dto
//        studentlist.add(new Student(1, "Math", "Physics", "Chemistry", 1));
//        studentlist.add(new Student(2, "Physics", "Chemistry", "Biology", 0));
//        studentlist.add(new Student(3, "Math", "Chemistry", "Biology", 1));
//        studentlist.add(new Student(4, "Physics", "Biology", "Geography", 0));
//        studentlist.add(new Student(5, "Math", "Physics", "Geography", 6));
//        studentlist.add(new Student(6, "Chemistry", "Biology", "Geography", 6));
        List<Student> studentlist = new sample().studentList;
        // Sample student data with subjects and
        Map<Integer, List<String>> students = new HashMap<>();
        for (Student student : studentlist)
        {
            List<String> subjecetlist = new ArrayList<>();
            if(student.getSubject1() != null)
            subjecetlist.add(student.getSubject1());
            if(student.getSubject2() != null)
            subjecetlist.add(student.getSubject2());
            if(student.getSubject3() != null)
            subjecetlist.add(student.getSubject3());
            students.put(student.getStudentId(), subjecetlist); //if different professor and same subject -> try use concat

        }
//        students.put(1, new ArrayList(Arrays.asList("Math", "Physics", "Chemistry"))); //if different professor and same subject -> try use concat
//        students.put(2, new ArrayList(Arrays.asList("Physics", "Chemistry", "Biology")));
//        students.put(3, new ArrayList(Arrays.asList("Math", "Chemistry", "Biology")));//1
//        students.put(4, new ArrayList(Arrays.asList("Physics", "Biology", "Geography")));
//        students.put(5, new ArrayList(Arrays.asList("Math", "Physics", "Geography")));//2
//        students.put(6, new ArrayList(Arrays.asList("Chemistry", "Biology", "Geography")));


//        if(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry")).containsAll(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry"))))
//                System.out.println("true");
        List<List<Integer>> newstudyGroups = new ArrayList<>();
        List<List<Integer>> friendstudyGroups = new ArrayList<>();

        Set<Integer> visitedStudents = new HashSet<>();


        //first matching
        for (Student outstudent : studentlist) {
            if (visitedStudents.contains(outstudent.getStudentId()) || outstudent.getGroupId() == 0)
                continue;
            List<Integer> group = new ArrayList<>();

            for (Student innerstudent : studentlist) {
                if (outstudent.getStudentId() == innerstudent.getStudentId() || innerstudent.getGroupId() == 0 || visitedStudents.contains(innerstudent.getStudentId()))
                    continue;
                if (outstudent.getGroupId() == innerstudent.getGroupId()) {
                    if (!visitedStudents.contains(outstudent.getStudentId())) {
                        visitedStudents.add(outstudent.getStudentId());
                        group.add(outstudent.getStudentId());
                    }
                    if (!visitedStudents.contains(innerstudent.getStudentId())) {
                        visitedStudents.add(innerstudent.getStudentId());
                        group.add(innerstudent.getStudentId());
                    }
                }
            }
            friendstudyGroups.add(group);
        }
        // Display the friend study groups
        System.out.println("after frist matcning");
        int groupNumber = 1;
        for (List<Integer> group : friendstudyGroups) {
            System.out.println("FriendGroup " + groupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            groupNumber++;
        }

        // second matching
        for (Map.Entry<Integer, List<String>> entry : students.entrySet()) {
            int studentId = entry.getKey();
            if (visitedStudents.contains(studentId)) {
                continue; // Skip if the student is already assigned to a group
            }

            List<String> currentSubjects = entry.getValue();
            List<Integer> group = new ArrayList<>();
            group.add(studentId);
            visitedStudents.add(studentId);


            // Find other students with matching subjects
            for (Map.Entry<Integer, List<String>> innerEntry : students.entrySet()) {
                int innerStudentId = innerEntry.getKey();
                if (studentId == innerStudentId || visitedStudents.contains(innerStudentId)) {
                    continue; // Skip if the student is already assigned to a group or is the same student
                }

                List<String> innerSubjects = innerEntry.getValue();

                if (matchstudy(currentSubjects, innerSubjects)) {
                    int length = 0;
                    for (Integer member : group) { //compare studentid of current group memeber to innerstudentid
                        if (member == studentId) // except studentId taking currentSubject
                            continue;
                        if (matchstudy(students.get(member), innerSubjects))
                            length++;
                    }
                    if (length == group.size() - 1) {//except studentId taking currentSubject
                        visitedStudents.add(innerStudentId);
                        group.add(innerStudentId);
                    }

                    if (group.size() > 5) {
                        break; // Stop adding students once the group has at least 3 members
                    }
                }
            }
            if (group.size() == 1) {
                visitedStudents.remove(studentId); //modified
            }

            // Add the group to the study groups list
            if (group.size() >= 2) {
                newstudyGroups.add(group);
            }
        }

        // Display the study groups
//        int groupNumber = 1;
        System.out.println();
        System.out.println("after second matching withtout friend group");
        for (List<Integer> group : newstudyGroups) {
            System.out.println("Group " + groupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            groupNumber++;
        }









        //3rd matcning newstudygroup
        for (List<Integer> group : newstudyGroups) {
            if (group.size() > 5)
                continue;
            int firstid = group.get(0);
            List<String> commonsubjects = new ArrayList<>(students.get(firstid));
            for (int studentId : group) {
                if (studentId == firstid)
                    continue;
                List<String> comparesubjects = students.get(studentId);
                commonsubjects.retainAll(comparesubjects); //remove not common element in commonsubjects and return true if commonsubjects changed as a result of the call
                if (commonsubjects.size() == 0)
                    break;
            }

            for (Map.Entry<Integer, List<String>> innerEntry : students.entrySet()) {
                int innerStudentId = innerEntry.getKey();
                if (visitedStudents.contains(innerStudentId)) {
                    continue; // Skip if the student is already assigned to a group or is the same student
                }
                List<String> innerSubjects = innerEntry.getValue();

                commonsubjects.retainAll(innerSubjects);  //remove not common element in commonsubjects and return true if commonsubjects changed as a result of the call
                if (commonsubjects.size() > 0) {
                    group.add(innerStudentId);
                    visitedStudents.add(innerStudentId);
                }
                if (group.size() > 5) {
                    break;
                }


            }

        }
        //3rd matcning friendstudygroup
        for (List<Integer> group : friendstudyGroups) {
            if (group.size() > 3)
                continue;
            int firstid = group.get(0);
            List<String> commonsubjects = new ArrayList<>(students.get(firstid));
            for (int studentId : group) {
                if (studentId == firstid)
                    continue;
                List<String> comparesubjects = students.get(studentId);
                commonsubjects.retainAll(comparesubjects); //remove not common element in commonsubjects and return true if commonsubjects changed as a result of the call
                if (commonsubjects.size() == 0)
                    break;

            }

            for (Map.Entry<Integer, List<String>> innerEntry : students.entrySet()) {
                int innerStudentId = innerEntry.getKey();
                if (visitedStudents.contains(innerStudentId)) {
                    continue; // Skip if the student is already assigned to a group or is the same student
                }
                List<String> innerSubjects = innerEntry.getValue();

                commonsubjects.retainAll(innerSubjects);  //remove not common element in commonsubjects and return true if commonsubjects changed as a result of the call
                if (commonsubjects.size() > 0) {
                    group.add(innerStudentId);
                    visitedStudents.add(innerStudentId);
                }
                if (group.size() > 3) {
                    break;
                }
            }

        }


        // Display the friend study groups
        System.out.println();
        System.out.println("after third matcning, friendtudygruop");
        int thirdgroupNumber = 1;
        for (List<Integer> group : friendstudyGroups) {
            System.out.println("FriendGroup " + thirdgroupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            thirdgroupNumber++;
        }

        // Display the study groups
//        int groupNumber = 1;
        System.out.println("after third matching, new study group");
        for (List<Integer> group : newstudyGroups) {
            System.out.println("Group " + thirdgroupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            thirdgroupNumber++;
        }

        //display not matching student
        System.out.println();
        System.out.println("not matching student");
        for(Student student : studentlist)
        {
            int id = student.studentId;
            if(visitedStudents.contains(id))
                continue;
            System.out.println("studentId " +id + " : " + students.get(id));
        }

    }

    static boolean matchstudy(List<String> currentSubjects, List<String> innerSubjects) {

        if (currentSubjects.containsAll(innerSubjects))
            return true;


        int length = innerSubjects.size();
        int match = 0;

        for (int j = 0; j < length; j++) {
            if (currentSubjects.contains(innerSubjects.get(j)))
                match++;
        }

        if (match > 1)
            return true;

        return false;
    }

}