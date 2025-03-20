class GraduateStudent extends Student {
    String specialization;

    GraduateStudent(String name, int rollNo, double marks, String specialization) {
        super(name, rollNo, marks);
        this.specialization = specialization;
    }

    void displayGraduateDetails() {
        display();
        System.out.println("Specialization: " + specialization);
    }

    public static void main(String[] args) {
        GraduateStudent gs = new GraduateStudent("Rahul", 102, 88.5, "Computer Science");
        gs.displayGraduateDetails();
    }
}