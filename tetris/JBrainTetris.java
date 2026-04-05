import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris{
    private Brain brain;
    private Brain.Move move;
    private JSlider slider;
    private JLabel label;
    private JCheckBox brainmode;
    private int mycount;
    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        mycount = -1;
    }
    @Override
    public JComponent createControlPanel(){
        JComponent brainPanel = super.createControlPanel();
        JPanel little = new JPanel();
        slider = new JSlider(0, 100, 0);
        slider.setPreferredSize(new Dimension(100, 15));
        label = new JLabel();
        brainmode = new JCheckBox("Brain active");
        little.add(brainmode);
        little.add(slider);
        little.add(new JLabel("Adversary:"));
        little.add(label);
        brainPanel.add(little);

        return brainPanel;
    }
    @Override
    public void tick(int verb){

        if (brainmode.isSelected() && verb == DOWN) {

            if (mycount != count) {
                mycount = count;
                board.undo();
                move = brain.bestMove(board, currentPiece, board.getHeight(), move);
            }
            if(move != null) {
                if (!move.piece.equals(currentPiece)){
                    super.tick(ROTATE);
                }
                if (move.x > currentX){
                    super.tick(RIGHT);
                }
                else if (move.x < currentX){
                    super.tick(LEFT);
                }
            }
        }

        super.tick(verb);
    }

    @Override
    public Piece pickNextPiece() {
        int randomvalue =random.nextInt(99);
        int percentage = slider.getValue();
        if (randomvalue>=percentage ) {
            label.setText("ok");
            return super.pickNextPiece();
        }
        Piece nextPiece = super.pickNextPiece();
        label.setText("*ok*");
        double badchoice = 0;
        for (int i = 0; i < pieces.length;i++) {
            Brain.Move possiblemove = brain.bestMove(board, pieces[i], board.getHeight(), move);
            if (possiblemove != null &&possiblemove.score > badchoice)   {
                nextPiece = pieces[i];
                badchoice = possiblemove.score;
            }
        }
        return nextPiece;



    }

    // copied from JTetris with slight changes
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}
