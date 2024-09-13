// Kindklasse SCHWEIN
class Schwein extends t_animal {
    public Schwein(String name, int alter) {
        super(name, alter);
    }

    @Override
    public void gibLaut() {
        System.out.println("Das Schwein sagt: Oinki!");
    }
}