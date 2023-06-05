import java.util.*;
import java.util.List;

public class Mainsecond {
    public static void main(String[] args) {


        List<Student> studentlist = new sample().studentList;
        // Sample student data with subjects and
        Map<Integer, List<String>> students = new HashMap<>();
        for (Student student : studentlist) {
            List<String> subjecetlist = new ArrayList<>();
            if (student.getSubject1() != null)
                subjecetlist.add(student.getSubject1());
            if (student.getSubject2() != null)
                subjecetlist.add(student.getSubject2());
            if (student.getSubject3() != null)
                subjecetlist.add(student.getSubject3());
            students.put(student.getStudentId(), subjecetlist); //if different professor and same subject -> try use concat

        }


//        if(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry")).containsAll(new ArrayList(Arrays.asList("Math", "Physics", "Chemistry"))))
//                System.out.println("true");
        List<List<Integer>> newstudyGroups = new ArrayList<>();
        List<List<Integer>> friendstudyGroups = new ArrayList<>();

        Set<Integer> visitedStudents = new HashSet<>();


        //first matching -> friend matching
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

        // second matching ->match_3or2subject 매서드 참조
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

                if (match_3or2subject(currentSubjects, innerSubjects)) {
                    int length = 0;
                    for (Integer member : group) { //compare studentid of current group memeber to innerstudentid
                        if (member == studentId) // except studentId taking currentSubject
                            continue;
                        if (match_3or2subject(students.get(member), innerSubjects))
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


        //third matching ->match_2subject 매서드 참조
        for (Map.Entry<Integer, List<String>> entry : students.entrySet()) {
            int studentId = entry.getKey();
            if (visitedStudents.contains(studentId)) {
                continue; // Skip if the student is already assigned to a group
            }

            List<String> currentSubjects = entry.getValue();
            List<Integer> group = new ArrayList<>();
            group.add(studentId);
            visitedStudents.add(studentId);


            for (Map.Entry<Integer, List<String>> innerEntry : students.entrySet()) {
                int innerStudentId = innerEntry.getKey();
                if (studentId == innerStudentId || visitedStudents.contains(innerStudentId)) {
                    continue; // Skip if the student is already assigned to a group or is the same student
                }

                List<String> innerSubjects = innerEntry.getValue();

                if (match_2subject(currentSubjects, innerSubjects)) {
                    int length = 0;
                    for (Integer member : group) { //compare studentid of current group memeber to innerstudentid
                        if (member == studentId) // except studentId taking currentSubject
                            continue;
                        if (match_2subject(students.get(member), innerSubjects))
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

        //4 matching -> match_only1subject 매서드 참조
        for (Map.Entry<Integer, List<String>> entry : students.entrySet()) {
            int studentId = entry.getKey();
            if (visitedStudents.contains(studentId)) {
                continue; // Skip if the student is already assigned to a group
            }

            List<String> currentSubjects = entry.getValue();
            List<Integer> group = new ArrayList<>();
            group.add(studentId);
            visitedStudents.add(studentId);


            for (Map.Entry<Integer, List<String>> innerEntry : students.entrySet()) {
                int innerStudentId = innerEntry.getKey();
                if (studentId == innerStudentId || visitedStudents.contains(innerStudentId)) {
                    continue; // Skip if the student is already assigned to a group or is the same student
                }

                List<String> innerSubjects = innerEntry.getValue();

                if (match_only1subject(currentSubjects, innerSubjects)) {
                    int length = 0;
                    for (Integer member : group) { //compare studentid of current group memeber to innerstudentid
                        if (member == studentId) // except studentId taking currentSubject
                            continue;
                        if (match_only1subject(students.get(member), innerSubjects))
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
        System.out.println("after 4 matching  withtout friend group");
        for (List<Integer> group : newstudyGroups) {
            System.out.println("Group " + groupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            groupNumber++;
        }

        //display not matching student -> 이 파트 이동해가면서 각 매칭후 얼만 그룹형성 안된 사람들있는지 체크가능
        System.out.println();
        System.out.println("not matching student");
        for (Student student : studentlist) {
            int id = student.studentId;
            if (visitedStudents.contains(id))
                continue;
            System.out.println("studentId " + id + " : " + students.get(id));
        }



        // 5 matcning friendstudygroup
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
                int findlastindex = innerSubjects.size();
                commonsubjects.retainAll(innerSubjects);  //remove not common element in commonsubjects and return true if commonsubjects changed as a result of the call
                if ( commonsubjects.size() > 0  )
                    // 비교 학생의 우선순위 3번쨰 과목이  친구그룹 공통과목과 일치만 아니면 친구 그룹에 추가
                    if (!(commonsubjects.size() == 1 && commonsubjects.contains(innerSubjects.get(findlastindex - 1)) && findlastindex == 3)) {
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
        System.out.println("after 5 matcning, friendtudygruop");
        int thirdgroupNumber = 1;
        for (List<Integer> group : friendstudyGroups) {
            System.out.println("FriendGroup " + thirdgroupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            thirdgroupNumber++;
        }

        // Display the study groups
//        int groupNumber = 1;
        System.out.println("after 5 matching, new study group");
        for (List<Integer> group : newstudyGroups) {
            System.out.println("Group " + thirdgroupNumber + ": ");
            for (int id : group)
                System.out.println("studentId " + id + " : " + students.get(id));
            thirdgroupNumber++;
        }

        //display not matching student -> 이 파트 이동해가면서 각 매칭후 얼만 그룹형성 안된 사람들있는지 체크가능
        System.out.println();
        System.out.println("not matching student");
        for (Student student : studentlist) {
            int id = student.studentId;
            if (visitedStudents.contains(id))
                continue;
            System.out.println("studentId " + id + " : " + students.get(id));
        }


    }

    static boolean match_3or2subject(List<String> currentSubjects, List<String> innerSubjects) {

        //우선 3개 모두 일치
        if (currentSubjects.size() > 2 && innerSubjects.size() > 2) {
            int length = 0;
            for (int j = 0; j < 2; j++)
                if (currentSubjects.get(j).equals(innerSubjects.get(j)))
                    length++;
            if (length > 2)
                return true;
        }

        int match1 = 0;  // 과목3개신청 and 우선 두개 일치( 사회, 과학 ,음악) (사회 과학 도덕)
        if (currentSubjects.size() > 1 && innerSubjects.size() > 1) {
            if (currentSubjects.get(0).equals(innerSubjects.get(0))) {
                if (currentSubjects.get(1).equals(innerSubjects.get(1)))
                    return true;
            }
        }


        return false;
    }

    static boolean match_2subject(List<String> currentSubjects, List<String> innerSubjects) {



        //과목2개 이상 신청시 first만 일치하고 나머지중 우선순위 관계없이 하나만 일치 (사회 과학) ( 사회 과학 도덕) or ( 사회 과학 도덕 ) (사회 도덕 과학)
        if (currentSubjects.size() > 1 && innerSubjects.size() > 1) {
            if (currentSubjects.get(0).equals(innerSubjects.get(0))) {
                if (currentSubjects.contains(innerSubjects.get(1)) || innerSubjects.contains(currentSubjects.get(1)))
                    return true;
            }
        }

        return false;
    }

    static boolean match_only1subject(List<String> currentSubjects, List<String> innerSubjects) {

        //가장 우선순위 하나만 일치
        if (currentSubjects.get(0).equals(innerSubjects.get(0)))
            return true;

        //교차일치
        if (currentSubjects.size() > 1 || innerSubjects.size() > 1)
            if (currentSubjects.get(0).equals(innerSubjects.get(1)) || currentSubjects.get(1).equals(innerSubjects.get(0)))
                return true;

        return false;
    }

}
