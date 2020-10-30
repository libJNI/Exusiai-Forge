package today.exusiai;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.ModuleManager;

public class ExternalGui extends JFrame
{
    public JPanel contentPane;
    private JPanel combat;
    private JPanel movement;
    private JPanel render;
    private JPanel player;
    private JPanel bedwars;

    public ExternalGui()
    {
        this.setVisible(false);
        this.setDefaultCloseOperation(1);
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout(new BorderLayout(0, 0));
        this.setContentPane(this.contentPane);
        JTabbedPane jtabbedpane = new JTabbedPane(1);
        this.contentPane.add(jtabbedpane);
        this.combat = new JPanel();
        jtabbedpane.addTab("Combat", this.combat);
        this.movement = new JPanel();
        jtabbedpane.addTab("Movement", this.movement);
        this.render = new JPanel();
        jtabbedpane.addTab("Visuals", this.render);
        this.player = new JPanel();
        jtabbedpane.addTab("Utility", this.player);
        this.bedwars = new JPanel();
        jtabbedpane.addTab("Other", this.bedwars);

        for (AbstractModule module : ModuleManager.getModules())
        {
            JRadioButton jradiobutton = new JRadioButton(module.getName());
            jradiobutton.setSelected(module.getState());
            jradiobutton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    module.toggle();
                }
            });

            if (module.getCategory() == AbstractModule.Category.Combat)
            {
                this.combat.add(jradiobutton);
            }

            if (module.getCategory() == AbstractModule.Category.Movement)
            {
                this.movement.add(jradiobutton);
            }

            if (module.getCategory() == AbstractModule.Category.Visuals)
            {
                this.render.add(jradiobutton);
            }

            if (module.getCategory() == AbstractModule.Category.Utility)
            {
                this.player.add(jradiobutton);
            }

            if (module.getCategory() == AbstractModule.Category.Other)
            {
                this.bedwars.add(jradiobutton);
            }
        }
    }
}
