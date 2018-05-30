import Nave.RenderizadorNave;
import Inimigo.RenderizadorInimigo;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ThayShooter implements GLEventListener, KeyListener {

    private final RenderizadorNave nave;
    private final RenderizadorInimigo ini;

    public ThayShooter() {

        JFrame telaPrincipal = new JFrame("ThayShooter");
        telaPrincipal.setBounds(50, 100, 1600, 900);
        telaPrincipal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        Container caixa = telaPrincipal.getContentPane();
        caixa.setLayout(layout);

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setRedBits(8);
        caps.setBlueBits(8);
        caps.setGreenBits(8);
        caps.setAlphaBits(8);

        nave = new RenderizadorNave();
        ini = new RenderizadorInimigo();

        GLCanvas canvas = new GLCanvas(caps);
        telaPrincipal.add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener((GLEventListener) ini);
        canvas.addMouseListener((MouseListener) ini);
        canvas.addKeyListener((KeyListener) ini);  
        telaPrincipal.setVisible(true);
        canvas.requestFocus();
    }

    public static void main(String[] args) {
        ThayShooter app = new ThayShooter();
    }

    @Override
    public void init(GLAutoDrawable glad) {
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }

    @Override
    public void display(GLAutoDrawable glad) {
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
