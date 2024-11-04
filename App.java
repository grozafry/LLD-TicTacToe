
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class InvalidMoveException extends Exception{
    public InvalidMoveException(String message){
        super(message);
    }
}

class Player{
    private String username;
    private String type;
    
    public Player(String username, String type){
        this.username = username;
        this.type = type;
    }

    public String getUsername(){
        return this.username;
    }
    
    public String getType(){
        return this.type;
    }
}

class Move{
    public int x;
    public int y;
    public Player player;
    private LocalDateTime moveTime;

    public Move(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
        this.moveTime = LocalDateTime.now();
    }
}

class Game{

    private String[][] board;
    private Player user1;
    private Player user2;
    private Player currPlayer;
    private Player winner;
    private Player loser;
    private List<Move> moves;
    private int size;

    public Game(int n, String username1, String username2){
        this.user1 = new Player(username1, "X");
        this.user2 = new Player(username2, "O");
        this.board = new String[n][n];
        this.size = n;
    }

    public void start(){
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.board[row][col] = "N"; // Or any default value like "-" or " "
            }
        }

        this.currPlayer = user1;
        this.moves = new ArrayList<>();
        System.out.println("Game started...");
        Scanner scanner = new Scanner(System.in);

        this.printBoard();
        
        while(true){
            System.out.println(this.currPlayer.getUsername() + " to move");
            System.out.println("Player " + this.currPlayer + ", enter your move (row and column): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Move currMove = new Move(this.currPlayer, x, y);
            try{
                this.makeMove(currMove);
            } 
            catch (InvalidMoveException e){
                System.out.println(e.getMessage());
                continue;
            }

            this.moves.add(currMove);
            if(this.currPlayer==user1){
                this.currPlayer = user2;
            }else{
                this.currPlayer = user1;
            }

            this.printBoard();


            if (this.isGameEnded(currMove)){
                this.winner = this.currPlayer;
                break;
            }
        }
        System.out.println("Game ended!");
        System.out.println(this.winner.getUsername() +  " is winner");
        // System.out.println(this.loser.getUsername() +  " is loser");
    }
    

    public void makeMove(Move move) throws InvalidMoveException{
        int row = move.x;
        int col = move.y;

        if(row<0 || row>=this.size || col<0 || col>=this.size){
            throw new InvalidMoveException("Check your move asshole!!"); 
        }else{
            this.board[row][col] = move.player.getType();
        }


    }

    private void printBoard(){
        for(int row=0; row<this.size; row++){
            for(int col=0; col<this.size; col++){
                // if (this.board[row][col]) {
                    
                // }
                System.out.print(this.board[row][col]);    
            }
            System.out.println();
        }
    }


    private boolean checkCol(Move move){
        String playerType = move.player.getType();

        // Check col
        int moveCol = move.y;
        for(int row=0; row<this.size; row++){
            if(this.board[row][moveCol]!=playerType){
                return false;
            }
        }
        return true;
    }

    private boolean checkRow(Move move){
        String playerType = move.player.getType();

        // Check row
        int moveRow = move.x;
        for(int col=0; col<this.size; col++){
            if(this.board[moveRow][col]!=playerType){
                return false;
            }
        }
        return true;
    }

    private boolean checkOneDiag(Move move){
        String playerType = move.player.getType();
        
        int moveCol = move.y;
        int moveRow = move.x;
        int count1 = 0;
        // Check one diagonal
        int col = moveCol;
        int row = moveRow;
        while(col>=0 && row>=0 && col<this.size && row<this.size){
            if(this.board[row][col]!=playerType){
                System.out.println("false");
                return false;
            }
            col++;
            row--;
            count1++;
        }
        col = moveCol;
        row = moveRow;
        int count2 = 0;
        while(col>=0 && row>=0 && col<this.size && row<this.size){
            if(this.board[row][col]!=playerType){
                System.out.println("false");
                return false;
            }
            col--;
            row++;
            count2++;
        }

        System.out.println("true");
        
        return count1+count2-1 == this.size;
    }

    private boolean checkTwoDiag(Move move){
        String playerType = move.player.getType();
        
        int moveCol = move.y;
        int moveRow = move.x;
        int count1 = 0;
        // Check other diagonal
        int col = moveCol;
        int row = moveRow;
        while(col>=0 && row>=0 && col<this.size && row<this.size){
            // System.out.println(row + " " +col);
            if(!this.board[row][col].equals(playerType)){
                System.out.println("false");
                return false;
            }
            col--;
            row--;
            count1++;
        }
        col = moveCol;
        row = moveRow;
        int count2 = 0;
        while(col>=0 && row>=0 && col<this.size && row<this.size){
            if(this.board[row][col]!=playerType){
                System.out.println("false");
                return false;
            }
            col++;
            row++;
            count2++;
        }

        System.out.println("true");
        return count1+count2-1 == this.size;
    }

    

    public boolean isGameEnded(Move move){
        
        return this.checkCol(move) || this.checkRow(move) || this.checkTwoDiag(move) || this.checkOneDiag(move);
    }
    
}

public class App {
    public static void main(String[] args) throws Exception {
        
        Game game = new Game(3, "suraj", "papa");
        game.start();

    }
}
