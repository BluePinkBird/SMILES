package macewan_dust.smiles;

import android.util.Log;

/**
 * ScoringAlgorithms class includes all methods for converting user input values into score values
 *
 * Also contains database methods to store and update scores
 *
 *
 * /////////////// getting scores will likely be done with a list and is not yet implemented -----------------
 *
 * Please use constants when calling functions to support maintainability
 * WARNING! Changing the text on buttons may require an update to the scoring rules.
 *
 * Note: booleans are not checked or reported in range checking
 */

public class ScoringAlgorithms {

    public static final String TAG = "ScoringAlgorithms";

    public static final int SCORE_UNDER = 1;
    public static final int SCORE_BALANCED = 2;
    public static final int SCORE_OVER = 3;
    public static final int SCORE_UNBALANCED = 4; // off balance input includes both high and low input
    public static final int SCORE_ERROR = 10000;
    public static final int SCORE_NO_DATA = 0;

    // input_x corresponds to the answer selected for all number range questions
    public static final int INPUT_a = 1;
    public static final int INPUT_b = 2;
    public static final int INPUT_c = 3;
    public static final int INPUT_d = 4;
    public static final int INPUT_e = 5;


    /**
     * Question A
     * sleepButton: button string
     * INPUT_a: < 4 hours
     * INPUT_b: 4-5 hours
     * INPUT_c: 6-9 hours
     * INPUT_d: > 9 hours
     *
     * @param sleepInput      integer corresponding to sleep amount as above. Use constants.
     * @param hinderanceCount integer corresponding to number of sleep interuptions as above
     * @return score integer corresponding to score constant
     */
    public static int scoreSleep(int sleepInput, int hinderanceCount) {

        // range checking. Note: hinderanceCount should be a max of 5 but could be increased
        // // without changing the scoring behavior, so has a higher range check
        if (sleepInput < INPUT_a || sleepInput > INPUT_d
                || hinderanceCount < INPUT_a || hinderanceCount > INPUT_d){
//            Log.e(TAG, "Error: invalid input, sleepInput: " + sleepInput +
  //                  " and hinderanceCount: " + hinderanceCount);
            return SCORE_ERROR;
        }

        // scoring
        if (sleepInput < INPUT_c || (sleepInput < INPUT_d && hinderanceCount > INPUT_b)) {
            return SCORE_UNDER;
        } else if (sleepInput == INPUT_c && hinderanceCount <= INPUT_b) {
            return SCORE_BALANCED;
        } else if (sleepInput > INPUT_c) {
            return SCORE_OVER;
        } else {
            Log.e(TAG, "Error: score for sleepInput: " + sleepInput +
                    " and hinderanceCount: " + hinderanceCount + " has no rules");
            return SCORE_ERROR;
        }
    }

    /**
     *
     * @param exerciseIndex 0 is low, 3 is high. all else are balanced
     * @param boneAndMuscle true is balanced
     * @param relaxationIndex 0 is low, 3 is high. all else are balanced
     * @return
     */
    public static int scoreMovement(int exerciseIndex, boolean boneAndMuscle, int relaxationIndex) {

        int balancedRule = 3; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (exerciseIndex < INPUT_a || exerciseIndex > INPUT_d
                || relaxationIndex < INPUT_a || relaxationIndex > INPUT_d){
 //           Log.e(TAG, "Error: invalid input, exerciseIndex: " + exerciseIndex +
   //                 " and relaxationIndex: " + relaxationIndex);
            return SCORE_ERROR;
        }

        // count
        switch (exerciseIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
            default:
                balancedCounter++;
                break;
        }

        if (boneAndMuscle)
            balancedCounter++;
        else
            lowCounter++;

        switch (relaxationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
            default:
                balancedCounter++;
                break;
        }

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for movement has no rule");
    }

    /**
     *
     * @param mindfulnessIndex index of button pushed
     * @param meditationIndex index of button pushed
     * @param imaginationIndex index of button pushed
     * @return score
     */
    public static int scoreImagination(int mindfulnessIndex, int meditationIndex, int imaginationIndex) {

        int balancedRule = 3; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (mindfulnessIndex < INPUT_a || mindfulnessIndex > INPUT_c
                || meditationIndex < INPUT_a || meditationIndex > INPUT_c
                || imaginationIndex < INPUT_a || imaginationIndex > INPUT_c){
//           Log.e(TAG, "Error: invalid input, mindfulnessIndex: " + mindfulnessIndex +
//                    ", meditationIndex: " + meditationIndex +
//                    " and imaginationIndex: " + imaginationIndex);
            return SCORE_ERROR;
        }

        // count
        switch (mindfulnessIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // count
        switch (meditationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // count
        switch (imaginationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for imagination has no rule");
    }

    /**
     * @param laughterInput is the same number as the user inputs from 1 - 5
     * @return balanced or low score constant
     */
    public static int scoreLaughter(int laughterInput) {

        // range checking
        if (laughterInput < INPUT_a || laughterInput > INPUT_e){
//            Log.e(TAG, "Error: invalid input, laughterInput: " + laughterInput);
            return SCORE_ERROR;
        }

        if (laughterInput < INPUT_e) {
            return SCORE_UNDER;
        } else if (laughterInput == INPUT_e) {
            return SCORE_BALANCED;
        } else {
  //          Log.e(TAG, "Error: score for laughterInput: " + laughterInput + "  has no rule");
            return SCORE_ERROR;
        }
    }

    /**
     * scoreEating - score eating algorithm
     * veg, protein, grain are all balanced at INPUT_b, low at INPUT_a, high is INPUT_c
     * @param vegIndex - INPUT_a to INPUT_c
     * @param proteinIndex - INPUT_a to INPUT_c
     * @param grainIndex - INPUT_a to INPUT_c
     * @param lessSodium - boolean
     * @param lessSugar - boolean
     * @return
     */
    public static int scoreEating(int vegIndex, int proteinIndex, int grainIndex,
                           boolean lessSodium, boolean lessSugar, boolean lessFat, boolean moreWater) {

        int balancedRule = 7; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (vegIndex < INPUT_a || vegIndex > INPUT_c
                || proteinIndex < INPUT_a || proteinIndex > INPUT_c
                || grainIndex < INPUT_a || grainIndex > INPUT_c){
            return SCORE_ERROR;
        }

        // count
        switch (vegIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        switch (proteinIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        switch (grainIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // sodium: true is balanced. false is high
        if (lessSodium)
            balancedCounter++;
        else
            highCounter++;

        // sugar: true is balanced, false is high
        if (lessSugar)
            balancedCounter++;
        else
            highCounter++;

        // fat: true is balanced, false is high
        if (lessFat)
            balancedCounter++;
        else
            highCounter ++;

        // water: true is balanced, false is low
        if (moreWater)
            balancedCounter ++;
        else
            lowCounter ++;

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for eating has no rule");
    }

    /**
     * Note: all booleans in the count code are adjusted to be true in the if statement.
     * For example, prevent is inverted with !
     *
     * @param speakingRating integer from 1 to 5 as entered by the user
     * @param debrief        true or false
     * @param notPrevented      true or false
     * @param socialMedia    true or false
     * @param impactHealth   true or false
     * @return high, low, balanced or unbalanced score constant
     */
    public static int scoreSpeaking(int speakingRating, boolean debrief, boolean notPrevented, boolean socialMedia, boolean impactHealth) {

        int balancedRule = 5; // the number of questions that must be balanced for an overall balanced score.

        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (speakingRating < INPUT_a || speakingRating > INPUT_e){
//            Log.e(TAG, "Error: invalid input, speakingRating: " + speakingRating);
            return SCORE_ERROR;
        }

        // counting
        if (speakingRating == INPUT_e)
            balancedCounter++;
        else
            lowCounter++;

        if (debrief)
            balancedCounter++;
        else
            lowCounter++;

        if (notPrevented)
            balancedCounter++;
        else
            lowCounter++;

        if (!socialMedia)
            balancedCounter++;
        else
            highCounter++;

        if (!impactHealth)
            balancedCounter++;
        else
            highCounter++;

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for speaking has no rule");
    }

    /**
     * Helper function for repeating code
     * @param balancedRule number of balanced answers to give a balanced score
     * @param highCounter number of high questions
     * @param balancedCounter number of high balanced
     * @param lowCounter number of high low
     * @param errorMessage text to customize error message.
     * @return score
     */
    private static int scoreHelper(int balancedRule, int highCounter,
                            int balancedCounter,int lowCounter, String errorMessage) {

        // returning score based on rules
        if (balancedCounter == balancedRule) {
            return SCORE_BALANCED;
        } else if (lowCounter == 0 && highCounter > 0) {
            return SCORE_OVER;
        } else if (highCounter == 0 && lowCounter > 0) {
            return SCORE_UNDER;
        } else if (highCounter > 0 && lowCounter > 0) {
            return SCORE_UNBALANCED;
        } else {
            Log.e(TAG, errorMessage);
            return SCORE_ERROR;
        }
    }
}
