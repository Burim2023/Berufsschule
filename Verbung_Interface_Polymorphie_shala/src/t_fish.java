// Kindklasse FISCH
class Fisch extends t_animal {
    public Fisch(String name, int alter) {
        super(name, alter);
    }

    @Override
    public void gibLaut() {
        System.out.println("Der Fisch macht: Blub Blub!");
    }
}