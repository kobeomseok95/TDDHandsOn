package numberguessing.console;

import java.util.stream.Stream;

import numberguessing.PositiveIntegerGenerator;

public final class AppModel {

    private final static String NEW_LINE = System.lineSeparator();
    private static final String SELECT_MODE = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
    private static final String LOW_MESSAGE = "Your guess is too low." + NEW_LINE + "Enter your guess: ";
    private static final String HIGH_MESSAGE = "Your guess is too high." + NEW_LINE + "Enter your guess: ";

    interface Processor {
        Processor run(String input);
    }

    private final PositiveIntegerGenerator generator;
    private boolean completed;
    private final StringBuffer outputBuffer;
    private Processor processor;
    
    public AppModel(PositiveIntegerGenerator generator) {
        this.generator = generator;
        outputBuffer = new StringBuffer(SELECT_MODE);
        completed = false;
        processor = this::processModeSelection;
    }

    private Processor processModeSelection(String input) {
        if (input.equals("1")) {
            outputBuffer.append("Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100."
            + NEW_LINE + "Enter your guess: ");
            int answer = generator.generateLessThanOrEqualToHundread();

            return getSinglePlayerGameProcessor(answer, 1);
        } else if (input.equals("2")) {
            outputBuffer.append("Multiplayer game" + NEW_LINE + "Enter player names separated with commas: ");
            return getMultiplayerGameProcessor();
        } else {
            completed = true;
            return null;
        }
    }

    private Processor getMultiplayerGameProcessor() {
        return input -> { 
            Object[] players = Stream.of(input.split(",")).map(String::trim).toArray();
            outputBuffer.append("I'm thinking of a number between 1 and 100." + "Enter " + players[0] + "'s guess: ");
            return input2 -> {
                outputBuffer.append("I'm thinking of a number between 1 and 100. Enter " + players[1] + "'s guess: ");
                return input3 -> {
                    outputBuffer.append("I'm thinking of a number between 1 and 100." + "Enter " + players[2] + "'s guess: ");
                    return null;
                };
            };
         };
    }

    private Processor getSinglePlayerGameProcessor(int answer, int tries) {
        return input -> {
            int guess = Integer.parseInt(input);
            if (guess < answer) {
                outputBuffer.append(LOW_MESSAGE);
                return getSinglePlayerGameProcessor(answer, tries + 1);
            } else if (guess > answer) {
                outputBuffer.append(HIGH_MESSAGE);
                return getSinglePlayerGameProcessor(answer, tries + 1);
            } else {
                outputBuffer.append("Correct! " + tries + (tries == 1 ? " guess." : " guesses.") + NEW_LINE + SELECT_MODE);
                return this::processModeSelection;
            }
        };
    }

    public boolean isCompleted() {
        return completed;
    }

    public String flushOutput() {
        String message = outputBuffer.toString();
        outputBuffer.setLength(0);
        return message;
    }

    public void processInput(String input) {
        processor = processor.run(input);
    }
}
