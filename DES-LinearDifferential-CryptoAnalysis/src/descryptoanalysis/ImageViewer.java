/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descryptoanalysis;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
* An application used for viewing images in disk files.
*/
public class ImageViewer extends JFrame
{
    
    public static final String APPNAME = "ImageViewer";
    
    public ImageViewer(Image image, boolean scaled, String title)
    {
        setTitle(title);
        imageComp = new ImageComponent(image, scaled);
        if (scaled)
            getContentPane().add(imageComp);
        else
            getContentPane().add(new JScrollPane(imageComp));
    }
    
    private ImageComponent imageComp = null;
    
    // ImageComponent is used for displaying the image
    protected class ImageComponent extends JComponent
    {
        private Image image = null;
        private boolean scaled = false;
        private Dimension size = null;
        private Insets insets = new Insets(0, 0, 0, 0);
        
        ImageComponent(Image image, boolean scaled)
        {
            this.image = image;
            this.scaled = scaled;
        }
        
        void setImage(Image image)
        {
            this.image = image;
            revalidate();
        }
        
        public void paint(Graphics g)
        {
            super.paint(g);
            insets = getInsets(insets);
            size = getSize(size);
            if (image == null)
                return;
            if (scaled)
                g.drawImage(image, 
            insets.left, insets.top, 
            size.width - insets.left - insets.right,
            size.height - insets.top - insets.bottom,
            this);
            else
                g.drawImage(image, insets.left, insets.top, this);
        }
        
        public Dimension getMinimumSize()
        {
            int imgw = 32, imgh = 32;
            if (image != null)
            {
                imgw = image.getWidth(this);
                imgh = image.getHeight(this);
            }
            insets = getInsets(insets);
            return new Dimension(
            insets.left + Math.max(32, imgw / 10) + insets.right,
            insets.top + Math.max(32, imgh / 10) + insets.bottom
            );
        }
        
        public Dimension getPreferredSize()
        {
            int imgw = 32, imgh = 32;
            if (image != null)
            {
                imgw = image.getWidth(this);
                imgh = image.getHeight(this);
            }
            insets = getInsets(insets);
            return new Dimension(
            insets.left + imgw + insets.right,
            insets.top + imgh + insets.bottom
            );
        }
        
        public Dimension getMaximumSize() { return getPreferredSize(); }
    }
    
    // An action class for opening a new image
    protected class LoadImageAction extends AbstractAction
    {
        LoadImageAction()
        {
            putValue(NAME, "Load Image...");
            putValue(SHORT_DESCRIPTION, "Load an image file from the local discs.");
        }
        
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new ExtensionFileFilter(
                new String[] {"gif", "GIF"}, "GIF image files");
            chooser.addChoosableFileFilter(filter);
            filter = new ExtensionFileFilter(
                new String[] {"jpg", "JPG", "jpeg", "JPEG"}, "JPG image files");
            chooser.addChoosableFileFilter(filter);
            filter = new ExtensionFileFilter(
                new String[] {"gif", "GIF", "jpg", "JPG", "jpeg", "JPEG"}, "GIF and JPG image files");
            chooser.addChoosableFileFilter(filter);
            chooser.removeChoosableFileFilter(
            chooser.getAcceptAllFileFilter());
            int returnVal = chooser.showOpenDialog(ImageViewer.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                String path = chooser.getSelectedFile().getPath();
                System.out.println(path);
                Image image = Toolkit.getDefaultToolkit().getImage(path);
                if (image != null)
                {
                    imageComp.setImage(image);
                    setTitle(APPNAME + ": " + path);
                }
            }
        }
    }
    
    // file filter for opening files with a specified extension
    protected class ExtensionFileFilter extends FileFilter
    {
        ExtensionFileFilter(String[] extensions, String description)
        {
            this.extensions = extensions;
            this.description = description;
        }
        
        public boolean accept(File f)
        {
            if (f.isDirectory())
                return true;
            
            String name = f.getName().toUpperCase();
            for (int i = 0; i < extensions.length; i++) 
                if (name.endsWith("." + extensions[i]))
                    return true;
            return false;
        }
        
        public String getDescription() { return description; }
        
        private String[] extensions;
        private String description;
    }
    
    protected class ExitAction extends AbstractAction
    {
        ExitAction() { putValue(NAME, "Exit"); }
        
        public void actionPerformed(ActionEvent e) { System.exit(0); }
    }
    
    public static void main(String ImageFile,String Title,boolean Scaled)
    {
           Image image = null;
           try {
                  image = Toolkit.getDefaultToolkit().getImage(ImageFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(
            "Parameters: [-scaled] [-file <file name>] [-url <URL]"
            );
            System.exit(0);
        }
        
        
        JFrame f = new ImageViewer(image, Scaled, Title);
                f.setSize(385,665);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        f.setVisible(true);
    }
    
}
