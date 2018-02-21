package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Erik
 *
 */
@SuppressWarnings("serial")
public class CreditsView 
	extends JPanel
{
	/** ... */
	private static final String ERIK = "Erik Tran";
	
	/** ... */
	private static final String FEZTIS = "Feztis";
	
	/** ... */
	private static final String LINUS = "Linus Aronsson";
	
	/** ... */
	private static final String JESPER = "Jesper";
	
	/** ... */
	private static final String DAVID = "David";
	
	/**
	 * 
	 * 
	 */
	public CreditsView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel(ERIK));
		add(new JLabel(LINUS));
		add(new JLabel(FEZTIS));
		add(new JLabel(JESPER));
		add(new JLabel(DAVID));
		setVisible(true);
	}
}
