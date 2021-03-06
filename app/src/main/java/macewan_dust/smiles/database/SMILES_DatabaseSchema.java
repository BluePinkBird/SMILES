package macewan_dust.smiles.database;

public class SMILES_DatabaseSchema {

    /**
     * Score user data table constants
     */
    public static final class ScoreTable {
        public static final String NAME = "scoreTable";

        /**
         * Column class
         */
        public static final class Columns {
            public static final String SCORE_ID = "uuid";
            public static final String DATE = "date";
            public static final String SLEEP = "sleep";
            public static final String MOVEMENT = "movement";
            public static final String IMAGINATION = "imagination";
            public static final String LAUGHTER = "laughter";
            public static final String EATING = "eating";
            public static final String SPEAKING = "speaking";
        }
    }

    /**
     * Raw user data table constants
     */
    public static final class RawTable {
        public static final String NAME = "rawTable";

        /**
         * Column class
         */
        public static final class Columns {
            public static final String RAW_ID = "uuid";
            public static final String DATE = "date";

            public static final String SLEEP1 = "sleep1_time";
            public static final String SLEEP2 = "sleep2_interruptions";

            public static final String MOVEMENT1 = "movement1_aerobic";
            public static final String MOVEMENT2 = "movement2_bone_and_muscle";
            public static final String MOVEMENT3 = "movement3_relaxation";

            public static final String IMAGINATION1 = "imagination1_mindfulness";
            public static final String IMAGINATION2 = "imagination2_meditation";
            public static final String IMAGINATION3 = "imagination3_creativity";

            public static final String LAUGHTER1 = "laughter1_rating";

            public static final String EATING1 = "eating1_vegetables";
            public static final String EATING2 = "eating2_grains";
            public static final String EATING3 = "eating3_protein";
            public static final String EATING4 = "eating4_sodium";
            public static final String EATING5 = "eating5_sugar";
            public static final String EATING6 = "eating6_fat";
            public static final String EATING7 = "eating7_water";

            public static final String SPEAKING1 = "speaking1_rating";
            public static final String SPEAKING2 = "speaking2_debrief";
            public static final String SPEAKING3 = "speaking3_prevented";
            public static final String SPEAKING4 = "speaking4_social_media";
            public static final String SPEAKING5 = "speaking5_negative_impact";
        }
    }
}
