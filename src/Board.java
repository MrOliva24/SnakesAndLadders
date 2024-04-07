import java.util.ArrayList;

public final class Board {
	private ArrayList<Square> squares = new ArrayList<>();
	private static final int MIN_NUM_SQUARES = 10;

	public Board(int numSquares, int[][] FromTo, int[] deathPos) {
		assert numSquares > MIN_NUM_SQUARES : "There must be at least " + MIN_NUM_SQUARES + " squares";
		makeSquares(numSquares);
		makeSnakesOrLadders(FromTo);
		makeDeaths(deathPos);
	}

	public Square firstSquare() {
		return squares.get(0);
	}

	public Square lastSquare() {
		return squares.get(squares.size()-1);
	}

	public Square findSquare(int position) {
		assert (position>0) && (position<numberOfSquares()) : "inexistent square";
		return squares.get(position);
	}

	public int numberOfSquares() {
		assert !squares.isEmpty();
		return squares.size();
	}

	private void makeSquares(int numSquares) {
		System.out.println("There are " + numSquares + " squares");
		squares.add(new FirstSquare(this));
		for (int position=1 ; position<numSquares ; position++) {
			Square square = new Square(position, this);
			squares.add(square);
		}
		assert squares.get(numSquares-1).isLastSquare();
	}

	private void makeSnakesOrLadders(int[][] FromTo) {

		for (int i=0; i<FromTo.length; i++) {


			int fromPosition = FromTo[i][0]-1;
			int toPosition = FromTo[i][1]-1;
			int transport = toPosition - fromPosition;

			if (transport < 0) {
				System.out.println("Snake from " + (fromPosition+1) + " to " + (toPosition+1));
			} else {
				System.out.println("Ladder from " + (fromPosition+1) + " to " + (toPosition+1));
			}
			squares.set(fromPosition, new SnakeOrLadder(fromPosition,this, transport));
		}
	}

	private void makeDeaths(int[] deathPos) {
		System.out.println("There are " + deathPos.length + " death squares:");
		for (int i=0; i<deathPos.length; i++) {
			int currentDeath = deathPos[i] - 1;
			System.out.println("Death square at position " + currentDeath);
			squares.set(currentDeath, new Death(currentDeath, this ));
		}

	}
}
