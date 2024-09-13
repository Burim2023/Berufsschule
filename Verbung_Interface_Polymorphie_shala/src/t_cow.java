// Kindklasse KUH
class Kuh extends t_animal {
    public Kuh(String name, int alter) {
        super(name, alter);
    }

    @Override
    public void gibLaut() {
        System.out.println("Die Kuh sagt: Muh MOO!");
    }
}
