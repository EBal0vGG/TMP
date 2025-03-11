package my_files.command;

public class TimedCommandDecorator implements Command {
    private final Command command;

    public TimedCommandDecorator(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        command.execute();
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + "мс");
    }
}
