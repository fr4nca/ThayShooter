package Inimigo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class RenderizadorInimigo extends MouseAdapter implements GLEventListener, KeyListener {

    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    private GLAutoDrawable glDrawable;
    private double angulo, aspecto;
    private float rotX, rotY, obsZ;
    private boolean luz;
    private Texture texturaNave;

    public RenderizadorInimigo() {
        angulo = 50;
        aspecto = 1;
        rotX = 0;
        rotY = 0;
        obsZ = 200;
    }

    public void init(GLAutoDrawable drawable) {
        glDrawable = drawable;
        gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);

        try {
            InputStream stream = getClass().getResourceAsStream("texturaNave.jpg");
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
            texturaNave = TextureIO.newTexture(data);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }
    
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        especificaParametrosVisualizacao();

        renderizaNave();
    }

    private void renderizaNave() {
        
        texturaNave.enable(gl);
        texturaNave.bind(gl);
        
        gl.glPushMatrix();
            corpo();
            gl.glPushMatrix();
                asas();
            gl.glPopMatrix(); 
            gl.glPushMatrix();
               gl.glTranslatef(18f, -2.6f, -4f);
               turbina();
            gl.glPopMatrix();    
            gl.glPushMatrix();
               gl.glTranslatef(-18f, -2.6f, -4f);
               turbina();
            gl.glPopMatrix();
        gl.glPopMatrix();
        
       
        texturaNave.disable(gl);
    }
    
    private void corpo() {
        
        GLUquadric cafe = glu.gluNewQuadric();
        
        glu.gluQuadricTexture(cafe, true);
        glu.gluQuadricDrawStyle(cafe, GLU.GLU_FILL);
        glu.gluQuadricNormals(cafe, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(cafe, GLU.GLU_OUTSIDE);
        gl.glScalef(1f, .7f, 1f);
        glu.gluCylinder(cafe, .1f, 6.3f, 8f, 50, 50);
        gl.glTranslatef(0f, 0f, 13f);
        glu.gluSphere(cafe, 8, 50, 50);
        glu.gluDeleteQuadric(cafe);
    }
    
    private void asas() {
        
        GLUquadric cafe = glu.gluNewQuadric();
        
        glu.gluQuadricTexture(cafe, true);
        glu.gluQuadricDrawStyle(cafe, GLU.GLU_FILL);
        glu.gluQuadricNormals(cafe, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(cafe, GLU.GLU_OUTSIDE);
        
        gl.glScalef(1.5f, .1f, .4f);
        gl.glTranslatef(0, -18f, -4f);
        glu.gluSphere(cafe, 12, 50, 50);
        glu.gluDeleteQuadric(cafe);
        
    }
    
    public void turbina() {
        GLUquadric cafe = glu.gluNewQuadric();
        glu.gluQuadricTexture(cafe, true);
        glu.gluQuadricDrawStyle(cafe, GLU.GLU_FILL);
        glu.gluQuadricNormals(cafe, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(cafe, GLU.GLU_OUTSIDE);
        
        gl.glPushMatrix();
        gl.glScalef(.2f, .2f, .2f);
        glu.gluCylinder(cafe, 5.5f, 5f, 20f, 50, 50);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glScalef(.176f, .176f, .2f);
        gl.glTranslatef(0f, 0f, -8f);
        glu.gluCylinder(cafe, .1f, 6.3f, 8f, 50, 50);
        gl.glPopMatrix();
        
        glu.gluDeleteQuadric(cafe);

    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
        aspecto = (float) width / (float) height;
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void posicionaObservador() {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -obsZ);
        gl.glRotatef(rotX, 1, 0, 0);
        gl.glRotatef(rotY, 0, 1, 0);
    }

    public void especificaParametrosVisualizacao() {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(angulo, aspecto, 0.2, 500);

        posicionaObservador();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) // Zoom in
        {
            if (angulo >= 4) {
                angulo -= 4;
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) // Zoom out
        {
            if (angulo <= 72) {
                angulo += 4;
            }
        }
        glDrawable.display();
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                rotY--;
                break;
            case KeyEvent.VK_RIGHT:
                rotY++;
                break;
            case KeyEvent.VK_UP:
                rotX++;
                break;
            case KeyEvent.VK_DOWN:
                rotX--;
                break;
            case KeyEvent.VK_HOME:
                obsZ++;
                break;
            case KeyEvent.VK_END:
                obsZ--;
                break;
            case KeyEvent.VK_F1:
                luz = !luz;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        glDrawable.display();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }
}
