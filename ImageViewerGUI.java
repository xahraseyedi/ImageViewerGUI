//https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
//برای grayscale کردن عکس از سایت استفاده شده

//https://stackoverflow.com/questions/10105521/how-to-change-the-contrast-and-brightness-of-an-image-stored-as-pixel-values
//برای تغییر روشنایی عکس از این سایت استفاده شده


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;


public class ImageViewerGUI extends JFrame implements ActionListener {

    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton ;
    JButton showBrightnessButton;
    JButton backButton;
    JPanel mainPanel;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;

    String filePath = "/Users/zahra/Documents/image";

    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);

    static int h = 900;
    static int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI(){

        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel();
    }

    public void mainPanel(){

        // Create main panel for adding to Frame
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        JLabel imageviewerlbl = new JLabel("Image Viewer");
        imageviewerlbl.setBounds(310,20,100,100);
        mainPanel.add(imageviewerlbl);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        buttonsPanel.setBounds(250,100,200,120);

        this.selectFileButton = new JButton("select");
        this.showImageButton = new JButton("show image");
        this.brightnessButton = new JButton("Brightness");
        this.grayscaleButton = new JButton("gray scale");
        this.resizeButton = new JButton("resize");
        this.closeButton = new JButton("Exit");

        selectFileButton.addActionListener( this);
        selectFileButton.setBackground(Color.WHITE);
        selectFileButton.setForeground(Color.darkGray);

        showImageButton.addActionListener( this);
        showImageButton.setBackground(Color.WHITE);
        showImageButton.setForeground(Color.darkGray);

        brightnessButton.addActionListener( this);
        brightnessButton.setBackground(Color.WHITE);
        brightnessButton.setForeground(Color.darkGray);

        grayscaleButton.addActionListener( this);
        grayscaleButton.setBackground(Color.WHITE);
        grayscaleButton.setForeground(Color.darkGray);

        resizeButton.addActionListener( this);
        resizeButton.setBackground(Color.WHITE);
        resizeButton.setForeground(Color.darkGray);

        closeButton.addActionListener( this);
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(Color.darkGray);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){

        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);
        resizePanel.setBounds(0,0,700,300);

        JLabel resizesectionlbl = new JLabel("Resize section");
        JLabel Widthlbl= new JLabel("Width:");
        JLabel Heightlbl= new JLabel("Height:");
        widthTextField = new JTextField();
        heightTextField= new JTextField();

        resizesectionlbl.setBounds(300,20,100,100);
        Widthlbl.setBounds(150,60,100,100);
        Heightlbl.setBounds(150,120,100,100);
        widthTextField.setBounds(200,90,350,40);
        heightTextField.setBounds(200,150,350,40);

        resizePanel.add(resizesectionlbl);
        resizePanel.add(Widthlbl);
        resizePanel.add(Heightlbl);
        resizePanel.add(widthTextField);
        resizePanel.add(heightTextField);

        showResizeButton = new JButton("Show Result");
        backButton = new JButton("Back");
        showResizeButton.setBounds(500,240,100,30);
        backButton.setBounds(100,240,100,30);

        resizePanel.add(showResizeButton);
        resizePanel.add(backButton);

        backButton.addActionListener(this);
        showResizeButton.addActionListener(this);

        this.remove(mainPanel);
        this.add(resizePanel);
        this.revalidate();
        this.repaint();
    }

    public void brightnessPanel(){

        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);
        brightnessPanel.setBounds(0,0,700,300);

        showBrightnessButton= new JButton("Show Result");
        backButton = new JButton("Back");

        showBrightnessButton.setBounds(500,240,100,30);
        backButton.setBounds(100,240,100,30);

        brightnessPanel.add(showBrightnessButton);
        brightnessPanel.add(backButton);

        backButton.addActionListener(this);
        showBrightnessButton.addActionListener(this);

        JLabel lbl1 = new JLabel("Enter f");
        JLabel lbl2 = new JLabel("(must be between 0 and 1)");

        lbl1.setBounds(150,60,100,100);
        lbl2.setBounds(90,80,300,100);

        brightnessPanel.add(lbl1);
        brightnessPanel.add(lbl2);

        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(290,100,200,40);
        brightnessPanel.add(brightnessTextField);

        this.remove(mainPanel);
        this.add(brightnessPanel);
        this.revalidate();
        this.repaint();
    }

    public void chooseFileImage(){

        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showOpenDialog(ImageViewerGUI.this);

        if( option == JFileChooser.APPROVE_OPTION ) {

            file = fileChooser.getSelectedFile();
        }
    }

    public void showOriginalImage() {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        try
        {
            BufferedImage image = ImageIO.read(file);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel showpicture = new JLabel();
            showpicture.setIcon(imageIcon);
            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);

            tempFrame.setVisible(true);
            tempFrame.setResizable(true);

            tempPanel.add(showpicture);
            tempFrame.add(tempPanel);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void grayScaleImage() {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        try
        {
            BufferedImage image = ImageIO.read(file);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs,null);
            image = op.filter(image,null);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel showpicture = new JLabel();
            showpicture.setIcon(imageIcon);
            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);

            tempFrame.setVisible(true);
            tempFrame.setResizable(true);

            tempPanel.add(showpicture);
            tempFrame.add(tempPanel);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void showResizeImage( int w1 , int h1 ) {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(String.valueOf(file)).getImage().getScaledInstance ( w1,h1 , Image.SCALE_DEFAULT));
        JLabel showpicture = new JLabel();
        showpicture.setIcon(imageIcon);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);

        tempFrame.setVisible(true);
        tempFrame.setResizable(true);

        tempPanel.add(showpicture);
        tempFrame.add(tempPanel);
    }

    public void showBrightnessImage( float f ) {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        try
        {
            BufferedImage image = ImageIO.read(file);
            RescaleOp op = new RescaleOp(f,0,null);
            image = op.filter(image,image);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel showpicture = new JLabel();
            showpicture.setIcon(imageIcon);
            tempPanel.setSize(1800, 1000);
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(1800, 1000);

            tempFrame.setVisible(true);
            tempFrame.setResizable(true);

            tempPanel.add(showpicture);
            tempFrame.add(tempPanel);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void actionPerformed( ActionEvent e ) {

        if( e.getSource() == resizeButton ){

            resizePanel();
        }

        else if( e.getSource() == showImageButton ) {

            showOriginalImage();
        }

        else if( e.getSource() == grayscaleButton ) {

            grayScaleImage();
        }

        else if( e.getSource() == showResizeButton ) {

            h = Integer.parseInt(heightTextField.getText());
            w = Integer.parseInt(widthTextField.getText());
            showResizeImage( w , h );
        }

        else if( e.getSource() == brightnessButton ) {

            brightnessPanel();
        }

        else if( e.getSource() == showBrightnessButton ) {

            brightenFactor = ( float ) Double.parseDouble( brightnessTextField.getText() );
            showBrightnessImage( ( float ) ( 1.0 -brightenFactor) );
        }

        else if( e.getSource() == selectFileButton ) {

            chooseFileImage();
        }

        else if( e.getSource()==closeButton ) {

            this.dispatchEvent( new WindowEvent(this , WindowEvent.WINDOW_CLOSING) );
        }

        else if( e.getSource() == backButton ) {

            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }

    public static void main(String[] args) {
        
        //create an object of ImageViewerGUI class
        new ImageViewerGUI();
    }
}
