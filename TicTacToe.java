import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private final Cell[][] cell = new Cell[3][3];
    private final Label lblStatus = new Label("X's turn to play ...");
    private char whoseTurn = 'X'; // X starts playing

    public static void main(String[] args) {
        launch(args);   // launch the TicTacToe application
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane(); // Grid pane to display cells of the game

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid.add(cell[i][j] = new Cell(), j, i); // add cells to grid
            }
        }

        // Customize the status label
        lblStatus.setFont(Font.font("LCDMono2", FontWeight.BOLD, FontPosture.ITALIC, 36));
        lblStatus.setTextFill(Color.GREEN);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(grid, lblStatus);
        Scene scene = new Scene(vbox, 500, 600);


        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe");
        primaryStage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/566/566294.png"));
        primaryStage.show();
    }


    // returns true if all cells have been clicked
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // returns true if the token has won the game
    public boolean isWon(char token) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token) {
                return true;
            }
        }
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (cell[0][i].getToken() == token && cell[1][i].getToken() == token && cell[2][i].getToken() == token) {
                return true;
            }
        }

        // Check diagonals
        if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token) {
            return true;
        }

        return cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token;
    }


    public class Cell extends StackPane {
        private final Text tokenText = new Text("");
        private char token = ' ';

        Cell() {
            this.setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.tokenText.setFont(Font.font("Bradley Hand ITC", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 80));
            this.getChildren().add(tokenText);
            this.setOnMouseClicked(event -> this.handleToken());
        }


        public void setTokenText(char turn) {
            this.tokenText.setText(String.valueOf(turn));
            if (turn == 'X') {
                this.tokenText.setFill(Color.GREEN);
            } else if (turn == 'O') {
                this.tokenText.setFill(Color.RED);
            }
        }

        public char getToken() {
            return token;
        }


        private void handleToken() {
            if (this.token == ' ' && whoseTurn == 'X') {
                setTokenText(whoseTurn);
                lblStatus.setText("O's turn to play ...");
                lblStatus.setTextFill(Color.RED);
                whoseTurn = 'O';
            } else if (this.token == ' ' && whoseTurn == 'O') {
                setTokenText(whoseTurn);
                lblStatus.setText("X's turn to play ...");
                lblStatus.setTextFill(Color.GREEN);
                whoseTurn = 'X';
            }

            this.token = whoseTurn;

            if (isWon(whoseTurn) && whoseTurn != 'D') {
                if (whoseTurn == 'X') {
                    lblStatus.setText("O HAS WIN !!");
                } else if (whoseTurn == 'O') {
                    lblStatus.setText("X HAS WIN !!");
                }
                lblStatus.setTextFill(Color.BLUE.brighter());
                whoseTurn = 'E';
            }

            if (isFull() && whoseTurn != 'E') {
                lblStatus.setText("Draw !! The game is over.");
                lblStatus.setTextFill(Color.GOLD.darker());
                whoseTurn = 'D';
            }
        }
    }
}
