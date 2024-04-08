public class Tile {

        private String color;
        private int number;
        private boolean isJoker;
        private boolean isFalseJoker;

        public Tile(String color, int number) {
                this.color = color;
                this.number = number;
                this.isJoker = false;
                this.isFalseJoker = false;
        }

        // constructor for joker tiles
        public Tile(String color, int number, boolean isJoker, boolean isFalseJoker) {
                this.color = color;
                this.number = number;
                this.isJoker = isJoker;
                this.isFalseJoker = isFalseJoker;
        }
        public String getColor() {
                return color;
        }

        public int getNumber() {
                return number;
        }
        public boolean isJoker() {
                return isJoker;
        }

        public boolean isFalseJoker() {
                return isFalseJoker;
        }

        @Override
        public String toString() {
                if (isJoker) {
                        return "Joker";
                }
                if (isFalseJoker) {
                        return "False Joker";
                }
                return color + "-" + number;
        }
}
