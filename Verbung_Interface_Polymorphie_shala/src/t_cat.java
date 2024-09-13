// Kindklasse KATZE
class Katze extends t_animal {
    public Katze(String name, int alter) {
        super(name, alter);
    }

    @Override
    public void gibLaut() {
        System.out.println("Die Katze sagt: Miau Miau!");
    }
}
