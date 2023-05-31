class Student {
    int studentId;
    String subject1; //priority 1
    String subject2; //priority 2
    String subject3; //priority 3
    int groupId;
    public Student(int studentId, String subject1, String subject2, String subject3, int groupId) {
        this.studentId = studentId;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
        this.groupId = groupId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}