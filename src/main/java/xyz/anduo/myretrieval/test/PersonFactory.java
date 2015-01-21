package xyz.anduo.myretrieval.test;


public class PersonFactory {

  public static Person createPerson(PersonType type) {
    Person person = null;
    switch (type) {
      case BOY:
        person = new Boy();
        break;
      case GIRL:
        person = new Girl();
        break;
    }
    return person;
  }

  public static void main(String[] args) {
    Person p1 = PersonFactory.createPerson(PersonType.BOY);
    p1.talk();
    p1.run();
    System.out.println("*****************");
    Person p2 = PersonFactory.createPerson(PersonType.GIRL);
    p2.talk();
    p2.run();
  }

}
