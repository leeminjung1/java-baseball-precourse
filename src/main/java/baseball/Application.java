package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private static final String INPUT_MESSAGE = "숫자를 입력해주세요 : ";
    private static final String BALL_MESSAGE = "볼";
    private static final String STRIKE_MESSAGE = "스트라이크";
    private static final String NOTHING_MESSAGE = "낫싱";
    private static final String WIN_MESSAGE = "3개의 숫자를 모두 맞히셨습니다! 게임 종료";
    private static final String RESTART_MESSAGE = "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.";

    public static void main(String[] args) {
		do {
			playGame();
		} while (askForRestart());
    }

    private static void playGame() {
        int[] answerArray = createRandomNumberArray();

        while (true) {
            String input = getUserInput();
            int[] guessArray = convertInputToGuessArray(input);
            if (printResultAndCheckWin(answerArray, guessArray)) {
                break;
            }
        }
    }

    private static String getUserInput() {
        System.out.print(INPUT_MESSAGE);
        String input = Console.readLine();
        validateGuessInput(input);
        return input;
    }

    private static int[] convertInputToGuessArray(String input) {
        int[] guess = new int[3];
        for (int i = 0; i < guess.length; i++) {
            guess[i] = Character.getNumericValue(input.charAt(i));
        }
        return guess;
    }

    private static boolean askForRestart() {
        System.out.println(RESTART_MESSAGE);
        String input = Console.readLine();
        validateRestartInput(input);
        return input.equals("1");
    }

    private static boolean printResultAndCheckWin(int[] answer, int[] guess) {
        int[] result = calculateStrikeAndBall(answer, guess);
        int strike = result[0];
        int ball = result[1];

        if (strike == 0 && ball == 0) {
            System.out.println(NOTHING_MESSAGE);
        } else if (ball == 0) {
            System.out.println(strike + STRIKE_MESSAGE);
        } else if (strike == 0) {
            System.out.println(ball + BALL_MESSAGE);
        } else {
            System.out.println(ball + BALL_MESSAGE + " " + strike + STRIKE_MESSAGE);
        }

        if (strike == 3) {
            System.out.println(WIN_MESSAGE);
            return true;
        }
        return false;
    }

    private static int[] calculateStrikeAndBall(int[] answer, int[] guess) {
        int strike = 0;
        int ball = 0;
        for (int i = 0; i < 3; i++) {
            if (answer[i] == guess[i]) {
                strike++;
            } else if (contains(answer, guess[i])) {
                ball++;
            }
        }
        return new int[] {strike, ball};
    }

    private static boolean contains(int[] array, int key) {
        for (int n : array) {
            if (n == key) {
                return true;
            }
        }
        return false;
    }

    private static void validateGuessInput(String input) {
        String pattern = "\\d{3}";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("3자리 숫자를 입력하세요.");
        }
    }

    private static void validateRestartInput(String input) {
        String pattern = "[12]";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("1 또는 2를 입력하세요.");
        }
    }

    private static int[] createRandomNumberArray() {
        int num1 = Randoms.pickNumberInRange(1, 9);
        int num2;
        do {
            num2 = Randoms.pickNumberInRange(1, 9);
        } while (num1 == num2);
        int num3;
        do {
            num3 = Randoms.pickNumberInRange(1, 9);
        } while (num3 == num1 || num3 == num2);

        return new int[]{num1, num2, num3};
    }
}