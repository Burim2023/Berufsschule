
// Abstrakte Elternklasse TIER
abstract class t_animal {
    // Zwei Eigenschaften
    protected String name;
    protected int alter;

    // Konstruktor
    public t_animal(String name, int alter) {
        this.name = name;
        this.alter = alter;
    }

    // Abstrakte Methode gibLaut()
    public abstract void gibLaut();
}
