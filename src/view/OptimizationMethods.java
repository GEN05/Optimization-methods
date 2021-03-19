package view;

import model.Function;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class OptimizationMethods extends JPanel {
    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is
     * if this component is opaque, you must completely fill in the background
     * in an opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */
    @Override
    protected void paintComponent(Graphics g) {
        int n = (int) ((Function.getRight() - Function.getLeft()) / 0.01);
        n++;
        int[] x = new int[n];
        int[] y = new int[n];
        int j = 0;
        for (double i = Function.getLeft(); i < Function.getRight(); i += 0.01) {
            x[j] = 800 + (int) (i * 100);
            y[j] = 200 + (int) (Function.calculate(i) * 100);
            j++;
        }
        Graphics2D drp = (Graphics2D) g;
        drp.drawLine(400, 400, 400, 0); // Center: x = 400, y = 200
        drp.drawLine(0, 200, 800, 200);
        drp.drawPolyline(x, y, n);
    }
}
