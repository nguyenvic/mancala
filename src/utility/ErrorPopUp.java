/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 * 
 * Models a pop up frame for when the user enters an invalid stone amount
 */

package utility;
import javax.swing.*;
import java.awt.*;

/**
 * Constructs a frame connected to the model to display error message
 */
public class ErrorPopUp extends JFrame
{
    private JLabel label;
    private JButton button;
    
    /**
     * Constructs the frame to display error message.
     */
    public ErrorPopUp() {
        super( "oops!" ); // invoke the JFrame constructor
        setPreferredSize(new Dimension(300,300));
        setResizable(false);
        setLayout(new BorderLayout());
        label = new JLabel("You should enter 3 or 4 stones.", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        button = new JButton("OK");// / construct a JLabel

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
                setVisible(false);
            }
        });

        add(label, BorderLayout.CENTER );
        add(button, BorderLayout.SOUTH);

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
        setLocationRelativeTo(null);
    }

}