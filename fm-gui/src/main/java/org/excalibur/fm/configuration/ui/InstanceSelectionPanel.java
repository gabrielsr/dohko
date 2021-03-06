/**
 *     Copyright (C) 2013-2014  the original author or authors.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License,
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.excalibur.fm.configuration.ui;

import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.text.NumberFormatter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.excalibur.core.cloud.api.InstanceTypes;
import org.excalibur.core.cloud.api.Provider;
import org.excalibur.core.cloud.api.domain.GeographicRegion;
import org.excalibur.core.services.ProviderService;
import org.excalibur.fm.configuration.io.util.ExportInstanceTypes;
import org.excalibur.fm.configuration.ui.model.GeographicRegionComboBoxModel;
import org.excalibur.fm.configuration.ui.model.InstanceTypeTableModel;
import org.excalibur.fm.configuration.ui.model.ProviderComboBoxModel;
import org.excalibur.fm.solver.constraints.Constraint;
import org.excalibur.fm.solver.constraints.Objective;
import org.excalibur.fm.solver.constraints.Operator;
import org.excalibur.fm.solver.constraints.SolutionType;
import org.excalibur.fm.solver.constraints.Variable;
import org.excalibur.fm.solver.constraints.problem.InstanceSelection;
import org.glassfish.jersey.jackson.JacksonFeature;

import static org.excalibur.fm.solver.constraints.SolutionType.ALL;
import static org.excalibur.fm.solver.constraints.SolutionType.OPTIMAL;
import static org.excalibur.fm.solver.constraints.SolutionType.PARETO_FRONT;
import static org.excalibur.fm.solver.constraints.Vars.CORES;
import static org.excalibur.fm.solver.constraints.Vars.COST;
import static org.excalibur.fm.solver.constraints.Vars.CPU;
import static org.excalibur.fm.solver.constraints.Vars.MEMORY;
import static org.excalibur.fm.solver.constraints.Vars.NET_THROUGHPUT;
import static org.excalibur.fm.solver.constraints.problem.InstanceSelection.*;

@SuppressWarnings({ "serial", "rawtypes" })
public class InstanceSelectionPanel extends javax.swing.JPanel
{
    private final List<Provider> providers_;
    private final List<GeographicRegion> regions_;
    private final ProviderService providerService_;
    private SolutionType solutionType_ = ALL;
    
    private Objective singleObjectiveToOptimize;
    
    private Constraint[] constraints_;
    
    private final Objective[] objectives_ =  
    {
        Objective.maximize(CORES),  
        Objective.maximize(MEMORY),
        Objective.maximize(CPU),
        Objective.minimize(COST)
    };

    /**
     * Creates new form InstanceSelection
     */
    public InstanceSelectionPanel(ProviderService providerService)
    {
        this.providerService_ = providerService;

        this.providers_ = providerService_.providers();
        this.regions_ = providerService_.allRegions();

        initComponents();
        
        ((NumberFormatter) ((JSpinner.NumberEditor) spnCpu.getEditor()).getTextField().getFormatter()).setAllowsInvalid(false);
        ((NumberFormatter) ((JSpinner.NumberEditor) spnFlops.getEditor()).getTextField().getFormatter()).setAllowsInvalid(false);
        ((NumberFormatter) ((JSpinner.NumberEditor) spnMemory.getEditor()).getTextField().getFormatter()).setAllowsInvalid(false);      
        ((NumberFormatter) tfCost.getFormatter()).setAllowsInvalid(false);
        this.lblMessage.setVisible(false);
        this.btnRecommend.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgOptions = new javax.swing.ButtonGroup();
        pnlTop = new javax.swing.JPanel();
        cbRegion = new javax.swing.JComboBox();
        cbProvider = new javax.swing.JComboBox();
        spnCpu = new javax.swing.JSpinner();
        spnMemory = new javax.swing.JSpinner();
        tfCost = new javax.swing.JFormattedTextField();
        lblRegion = new javax.swing.JLabel();
        lblProvider = new javax.swing.JLabel();
        lblvCpu = new javax.swing.JLabel();
        lblMemory = new javax.swing.JLabel();
        lblCost = new javax.swing.JLabel();
        rbAllSolution = new javax.swing.JRadioButton();
        rbOptimal = new javax.swing.JRadioButton();
        rbParetoFront = new javax.swing.JRadioButton();
        spnFlops = new javax.swing.JSpinner();
        lblGlops = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInstanceTypes = new javax.swing.JTable();
        plnBottom = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnDeploy = new javax.swing.JButton();
        lblMessage = new javax.swing.JLabel();
        btnRecommend = new javax.swing.JButton();

        setMaximumSize(null);
        setPreferredSize(new java.awt.Dimension(1275, 406));

        cbRegion.setModel(new GeographicRegionComboBoxModel(regions_));

        cbProvider.setModel(new ProviderComboBoxModel(providers_));

        spnCpu.setModel(new javax.swing.SpinnerNumberModel(1, 1, 32, 1));

        spnMemory.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1024, 1));
        spnMemory.setToolTipText("");

        tfCost.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));

        lblRegion.setText("Region");

        lblProvider.setText("Provider");

        lblvCpu.setText("vCPU");
        lblvCpu.setFocusable(false);

        lblMemory.setText("Memory (GB)");

        lblCost.setText("Cost (USD)");

        bgOptions.add(rbAllSolution);
        rbAllSolution.setSelected(true);
        rbAllSolution.setText("All solutions");
        rbAllSolution.setToolTipText("All valid solutions");
        rbAllSolution.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbOptimalItemStateChanged(evt);
            }
        });

        bgOptions.add(rbOptimal);
        rbOptimal.setText("Best solutions");
        rbOptimal.setToolTipText("Best solutions");
        rbOptimal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbOptimalItemStateChanged(evt);
            }
        });

        bgOptions.add(rbParetoFront);
        rbParetoFront.setText("Pareto front");
        rbParetoFront.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbOptimalItemStateChanged(evt);
            }
        });

        spnFlops.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1024, 1));

        lblGlops.setText("ECU (GFlops)");

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProvider)
                    .addGroup(pnlTopLayout.createSequentialGroup()
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(cbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRegion)))
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(rbAllSolution)
                                .addGap(18, 18, 18)
                                .addComponent(rbOptimal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbParetoFront)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(spnCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTopLayout.createSequentialGroup()
                                .addComponent(lblvCpu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMemory)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblGlops, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spnFlops, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCost, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCost))))
                .addGap(0, 411, Short.MAX_VALUE))
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup()
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegion)
                    .addComponent(lblProvider)
                    .addComponent(lblvCpu)
                    .addComponent(lblMemory)
                    .addComponent(lblCost)
                    .addComponent(lblGlops))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnCpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnMemory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnFlops, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbAllSolution)
                    .addComponent(rbOptimal)
                    .addComponent(rbParetoFront))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblRegion.getAccessibleContext().setAccessibleName("");

        tblInstanceTypes.setModel(new InstanceTypeTableModel(new InstanceTypes()));
        jScrollPane1.setViewportView(tblInstanceTypes);

        btnClose.setText("Close");
        btnClose.setToolTipText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnFind.setText("Search");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnDeploy.setText("Deploy");
        btnDeploy.setEnabled(false);
        btnDeploy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeployActionPerformed(evt);
            }
        });

        lblMessage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMessage.setText("There is/are # solution(s)");

        btnRecommend.setText("Suggests me one");
        btnRecommend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecommendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plnBottomLayout = new javax.swing.GroupLayout(plnBottom);
        plnBottom.setLayout(plnBottomLayout);
        plnBottomLayout.setHorizontalGroup(
            plnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plnBottomLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRecommend)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(plnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plnBottomLayout.createSequentialGroup()
                        .addComponent(btnDeploy, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        plnBottomLayout.setVerticalGroup(
            plnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plnBottomLayout.createSequentialGroup()
                .addComponent(lblMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(plnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeploy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecommend))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plnBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plnBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
   
    private void btnRecommendActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_btnRecommendActionPerformed

        lblMessage.setVisible(false);
        final ObjectivePopup popup = new ObjectivePopup();
        popup.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                Variable var = popup.getSelectedVar();

                Constraint[] newConstraits = new Constraint[constraints_.length - 1];
                Objective[] objectives = new Objective[objectives_.length];

                for (int i = 0, j = 0; i < constraints_.length; i++)
                {
                    if (!constraints_[i].getVariable().getName().equalsIgnoreCase(var.getName()))
                    {
                        newConstraits[j++] = constraints_[i];
                    }
                }

                for (int i = 0; i < newConstraits.length; i++)
                {
                    objectives[i] = Objective.valueOf(newConstraits[i]);
                }

                objectives[objectives.length - 1] = Objective.maximize(NET_THROUGHPUT);
                tryToRecommendOneSolution(objectives, newConstraits);
            }
        });
       
       java.awt.EventQueue.invokeLater(new Runnable() 
       {
           public void run() 
           {
               popup.setVisible(true);
           }
       });
       
       
    }                                            

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_btnCloseActionPerformed
        System.exit(0);
    }// GEN-LAST:event_btnCloseActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_btnFindActionPerformed
        Provider provider = null;
        GeographicRegion region = null;
        this.btnRecommend.setVisible(false);
        
        Number value = (Number) tfCost.getValue();
        
        if (tfCost.getValue() != null && 0 < value.doubleValue())
        {
        	if (cbProvider.getSelectedIndex() > 0)
            {
                provider = this.providers_.get(cbProvider.getSelectedIndex() - 1);
            }

            if (cbRegion.getSelectedIndex() > 0)
            {
                region = this.regions_.get(cbRegion.getSelectedIndex() - 1);
            }

            int maximumCost = new BigDecimal(tfCost.getText()).multiply(InstanceSelection.COST_FACTOR).intValue();
            constraints_ = new Constraint[4];
            
            constraints_[0] = new Constraint(new Variable(CORES, (int) spnCpu.getModel().getValue()), Operator.GE);
            constraints_[1] = new Constraint(new Variable(CPU, ((int) spnFlops.getValue()) * SUSTAINABLE_PERFORMANCE_FACTOR.intValue()), Operator.GE);
            constraints_[2] = new Constraint(new Variable(MEMORY, (int) spnMemory.getModel().getValue() * 1024), Operator.GE);
            constraints_[3] = new Constraint(new Variable(COST, maximumCost), Operator.LE);
            
            InstanceTypes allInstanceTypes = this.providerService_.getInstanceTypes(provider, region);
            InstanceSelection selector = new InstanceSelection(allInstanceTypes);
            InstanceTypes solutions = new InstanceTypes();
            
            this.exportSolutions(allInstanceTypes, "all-instance-types.tsv");

            switch (this.solutionType_)
            {
            case ALL:
                solutions = selector.findAllSolutions(constraints_);
                break;
            case OPTIMAL:
//                solutions = selector.findAllOptimalSolutions(MINIMIZE, constraints_, COST);
                solutions = selector.findAllOptimalSolutions(singleObjectiveToOptimize, constraints_);
                break;
            case PARETO_FRONT:              
                // validInstanceTypes = selector.findParetoFront(MAXIMIZE, contraints, CORES, MEMORY);
                solutions = selector.findParetoFront(constraints_, objectives_);
                this.btnRecommend.setVisible(solutions.size() > 1);
                break;
            }

            tblInstanceTypes.setModel(new InstanceTypeTableModel(solutions));
            btnDeploy.setEnabled(!solutions.isEmpty());
            showStatusMessage(solutions);
            exportSolutions(solutions);
        }
    }// GEN-LAST:event_btnFindActionPerformed

    
    private void exportSolutions(InstanceTypes types)
    {
        this.exportSolutions(types, this.solutionType_.name() + ".tsv");
    }
    
    private void exportSolutions(InstanceTypes types, String name)
    {
        try
        {
            try (OutputStream output = new FileOutputStream(new File(".", name)))
            {
                new ExportInstanceTypes(types, output).export();
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    private void tryToRecommendOneSolution(Objective[] objectives, Constraint ... constraints)
    {
        InstanceTypes rows = ((InstanceTypeTableModel) this.tblInstanceTypes.getModel()).getRows();
        System.out.println(this.getSize());
        
        if (rows.size() > 1)
        {
            InstanceSelection selector = new InstanceSelection(rows);
            InstanceTypes solution = selector.findParetoFront(constraints, objectives);
            
            tblInstanceTypes.setModel(new InstanceTypeTableModel(solution));
        }
        this.btnRecommend.setVisible(((InstanceTypeTableModel) this.tblInstanceTypes.getModel()).getRows().size() > 1);
    }
    
    
    private void showStatusMessage(InstanceTypes solutions)
    {
        this.lblMessage.setVisible(!solutions.isEmpty());
        
        String[][] msg = {{"is", "are"}, {"solution", "solutions"}}; 
        
        if (!solutions.isEmpty())
        {
            int i = (solutions.size() <  1) ? 0 : 1;
            
            this.lblMessage.setText(String.format("There %s %s %s",msg[0][i], solutions.size(), msg[1][i]));
        }
    }

    private void rbOptimalItemStateChanged(java.awt.event.ItemEvent evt)
    {// GEN-FIRST:event_rbOptimalItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            if (this.rbAllSolution.equals(evt.getSource()))
            {
                this.solutionType_ = ALL;
            }
            else if (this.rbOptimal.equals(evt.getSource()))
            {
                this.solutionType_ = OPTIMAL;
                
                final BestOptionPopup popup = new BestOptionPopup();
                popup.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        singleObjectiveToOptimize = popup.getObjective();
                        btnFindActionPerformed(null);
                    }
                });
                
                java.awt.EventQueue.invokeLater(new Runnable() 
                {
                    public void run() 
                    {
                        popup.setVisible(true);
                    }
                });
            }
            else
            {
                this.solutionType_ = PARETO_FRONT;
            }
            
            if (!OPTIMAL.equals(this.solutionType_))
            {
                btnFindActionPerformed(null);
            }
        }
    }// GEN-LAST:event_rbOptimalItemStateChanged

    private void btnDeployActionPerformed(java.awt.event.ActionEvent evt)
    {// GEN-FIRST:event_btnDeployActionPerformed
        
        Client client = ClientBuilder.newClient().register(ObjectMapperProvider.class).register(JacksonFeature.class);
        InstanceTypes instanceTypes = ((InstanceTypeTableModel) tblInstanceTypes.getModel()).getSelectedRows();
        
        if (!instanceTypes.isEmpty())
        {
            // @Path("types/{username}/deploy")
            Response response = client.target("http://localhost:8080/deployment").path("types").path(System.getProperty("user.name")).path("deploy").request()
                    .post(Entity.entity(instanceTypes, MediaType.APPLICATION_XML_TYPE));
            
            System.out.println(response.getStatus());
        }

    }// GEN-LAST:event_btnDeployActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgOptions;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDeploy;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnRecommend;
    private javax.swing.JComboBox cbProvider;
    private javax.swing.JComboBox cbRegion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblGlops;
    private javax.swing.JLabel lblMemory;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblProvider;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JLabel lblvCpu;
    private javax.swing.JPanel plnBottom;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JRadioButton rbAllSolution;
    private javax.swing.JRadioButton rbOptimal;
    private javax.swing.JRadioButton rbParetoFront;
    private javax.swing.JSpinner spnCpu;
    private javax.swing.JSpinner spnFlops;
    private javax.swing.JSpinner spnMemory;
    private javax.swing.JTable tblInstanceTypes;
    private javax.swing.JFormattedTextField tfCost;
    // End of variables declaration//GEN-END:variables
}
