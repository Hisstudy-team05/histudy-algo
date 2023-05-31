import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Student> studentlist = new ArrayList<>(); // from Dto
        studentlist.add(new Student(1, "Math", "Physics", "Chemistry", 1));
        studentlist.add(new Student(2, "Physics", "Chemistry", "Biology", 0));
        studentlist.add(new Student(3, "Math", "Chemistry", "Biology", 1));
        studentlist.add(new Student(4, "Physics", "Biology", "Geography", 0));
        studentlist.add(new Student(5, "Math", "Physics", "Geography", 6));
        studentlist.add(new Student(6, "Chemistry", "Biology", "Geography", 0));
        // Sample student data with subjects and
        Map<Integer, List<String>> students = new HashMap<>();
        students.put(11, new ArrayList(Arrays.asList("Math", "Physics", "Chemistry"))); //if different proffesor and same subject -> try use concat
        students.put(2, new ArrayList(Arrays.asList("Physics", "Chemistry", "Biology")));
        students.put(3, new ArrayList(Arrays.asList("Math", "Chemistry", "Biology")));//1
        students.put(4, new ArrayList(Arrays.asList("Physics", "Biology", "Geography")));
        students.put(5, new ArrayList(Arrays.asList("Math", "Physics", "Geography")));//2
        students.put(6, new ArrayList(Arrays.asList("Chemistry", "Biology", "Geography")));

//        if(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry")).containsAll(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry"))))
//                System.out.println("true");
        List<List<Integer>> studyGroups = new ArrayList<>();

        Set<Integer> visitedStudents = new HashSet<>();

        for (Map.Entry<Integer, List<String>> entry : students.entrySet()) {
            int studentId = entry.getKey();
            if (visitedStudents.contains(studentId)) {
                continue; // Skip if the student is already assigned to a group
            }

            List<String> currentSubjects = entry.getValue();
            List<Integer> group = new ArrayList<>();
            group.add(studentId);
            visitedStudents.add(studentId);

            //still developing
//            // priority matching for people wanting to form group together
//            for (Student outstudent : studentlist) {
//                if (outstudent.getGroupId() == 0)
//                    continue;
//                for (Student innerstudent : studentlist) {
//                    if (innerstudent.getGroupId() == 0)
//                        continue;
//                    if (outstudent.getGroupId() == innerstudent.getGroupId()) {
//                        visitedStudents.add(outstudent.getStudentId());
//                        visitedStudents.add(innerstudent.getStudentId());
//                    }
//                }
//            }

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

                    if (group.size() >= 3) {
                        break; // Stop adding students once the group has at least 3 members
                    }
                }
            }
            if (group.size() == 1) {
                visitedStudents.remove(visitedStudents.size() - 1);
            }

            // Add the group to the study groups list
            if (group.size() >= 2) {
                studyGroups.add(group);
            }
        }

        // Display the study groups
        int groupNumber = 1;
        for (List<Integer> group : studyGroups) {
            System.out.println("Group " + groupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            groupNumber++;
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