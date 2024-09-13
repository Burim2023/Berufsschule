// Kindklasse HUND
class Hund extends t_animal {
    public Hund(String name, int alter) {
        super(name, alter);
    }

    @Override
    public void gibLaut() {
        System.out.println("Der Hund sagt: Wuff WUFF!");
    }
}
