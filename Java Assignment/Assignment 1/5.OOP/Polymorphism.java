class Parent {
    void show() {
        System.out.println("Parent class method");
    }
}

class Child extends Parent {
    void show() {  // Overriding
        System.out.println("Child class method");
    }

    void show(String msg) {  // Overloading
        System.out.println("Message: " + msg);
    }

    public static void main(String[] args) {
        Child c = new Child();
        c.show();
        c.show("Hello, Java!");
    }
}