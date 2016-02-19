/**
 * Created by HörðurMár on 19.2.2016.
 */
public class Evaluate {

    BoardSquare[][] board;
    State state;

    public Evaluate(State state){
        this.board = state.board;
        this.state = state;
    }

    /*
     * The evaluation given in the assignment description.
     */
    public int evaluateDummy()
    {
        int boardWidth = this.board.length;
        int boardHeight = this.board[0].length;

        int heightOfMostAdvancedWhite = 0;
        int heightOfMostAdvancedBlack = 0;

        blackLoop:
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (board[i][j] == BoardSquare.Black)
                {
                    heightOfMostAdvancedBlack = j;
                    break blackLoop;
                }
            }
        }

        whiteLoop:
        for (int j = boardHeight - 1; j >= 0; j--)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (board[i][j] == BoardSquare.White)
                {
                    heightOfMostAdvancedWhite = j;
                    break whiteLoop;
                }
            }
        }

        int distMostAdvancedBlack = heightOfMostAdvancedBlack;
        int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

        return 50 + distMostAdvancedWhite - distMostAdvancedBlack;
    }

    /*
     * A new evaluation function.
     */
    public int centerAttack() {

        int boardWidth = this.board.length;
        int boardHeight = this.board[0].length;



        return 1;
    }


}
