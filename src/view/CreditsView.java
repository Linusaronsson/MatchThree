package view;

import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ...
 *
 * @author Erik
 */
@SuppressWarnings("serial")
public class CreditsView
	extends JPanel
{
	/** ... */
	private static final JLabel ERIK = new JLabel("Erik Tran");
	
	/** ... */
	private static final JLabel FEZTIS = new JLabel("Feztis");
	
	/** ... */
	private static final JLabel LINUS = new JLabel("Linus Aronsson");
	
	/** ... */
	private static final JLabel JESPER = new JLabel("Jesper");
	
	/** ... */
	private static final JLabel DAVID = new JLabel("David");
	
	/**
	 * ...
	 */
	public CreditsView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Set label properties //
		setLabelProperties(ERIK, FEZTIS, LINUS, JESPER, DAVID);
		
		// Assemble view //
		add(ERIK);
		add(FEZTIS);
		add(LINUS);
		add(JESPER);
		add(DAVID);
		
	}
	
	/**
	 * ...
	 *
	 * @param labels ...
	 */
	private void setLabelProperties(JLabel... labels) {
		for (JLabel label : labels) {
			label.setFont(new Font("Impact", Font.PLAIN, 20));
		}
	}
}
