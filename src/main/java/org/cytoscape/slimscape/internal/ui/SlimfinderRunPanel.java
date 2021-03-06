package org.cytoscape.slimscape.internal.ui;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.*;
import org.cytoscape.slimscape.internal.AlterGraph;
import org.cytoscape.slimscape.internal.CommonMethods;
import org.cytoscape.slimscape.internal.RunSlimfinder;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/*
 * @author: Kevin O'Brien
 * @author: Emily SW
 */
public class SlimfinderRunPanel extends JPanel {

    JTextArea idTextArea = null;
    JTextArea uniprotTextArea = null;
    CyApplicationManager manager;
    List<String> input;
    CyNetworkFactory networkFactory;
    CyNetworkManager networkManager;
    CyNetworkViewFactory networkViewFactory;
    CyNetworkViewManager networkViewManager;
    VisualMappingManager visualMappingManager;
    CyEventHelper eventHelper;
    OpenBrowser openBrowser;
    JTabbedPane slimfinder;
    JButton runSLiMFinderButton;
    CyAppAdapter adapter;

    public SlimfinderRunPanel(final CyApplicationManager manager, final OpenBrowser openBrowser,
                              final SlimfinderOptionsPanel optionsPanel, final CyEventHelper eventHelper,
                              final CyNetworkFactory networkFactory, final CyNetworkManager networkManager,
                              final CyNetworkViewFactory networkViewFactory, final CyNetworkViewManager networkViewManager,
                              final VisualMappingManager visualMappingManager, final CyAppAdapter adapter, JTabbedPane slimfinder) {
        this.networkFactory = networkFactory;
        this.manager = manager;
        this.networkManager = networkManager;
        this.networkViewFactory = networkViewFactory;
        this.networkViewManager = networkViewManager;
        this.visualMappingManager = visualMappingManager;
        this.eventHelper = eventHelper;
        this.openBrowser = openBrowser;
        this.slimfinder = slimfinder;
        this.adapter = adapter;

        setBackground(new Color(238, 238, 238));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{629, 0};
        gridBagLayout.rowHeights = new int[]{170, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        setPreferredSize(new Dimension(476, 167));

        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;

        JPanel sLiMFinderPanel = new JPanel();
        sLiMFinderPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder("Run SLiMFinder"),
                new EmptyBorder(0, 0, 0, 20)));
        GridBagConstraints gbc_sLiMFinderPanel = new GridBagConstraints();
        gbc_sLiMFinderPanel.fill = GridBagConstraints.BOTH;
        gbc_sLiMFinderPanel.gridx = 0;
        gbc_sLiMFinderPanel.gridy = 0;
        add(sLiMFinderPanel, gbc_sLiMFinderPanel);
        GridBagLayout gbl_sLiMFinderPanel = new GridBagLayout();
        gbl_sLiMFinderPanel.columnWidths = new int[]{497, 0};
        gbl_sLiMFinderPanel.rowHeights = new int[]{25, 87, 0};
        gbl_sLiMFinderPanel.columnWeights = new double[]{1.0,
                Double.MIN_VALUE};
        gbl_sLiMFinderPanel.rowWeights = new double[]{0.0, 0.0,
                Double.MIN_VALUE};
        sLiMFinderPanel.setLayout(gbl_sLiMFinderPanel);

        JPanel slimSearchOptionsPanel = new JPanel();
        slimSearchOptionsPanel.setBorder(new TitledBorder(new LineBorder(
                new Color(184, 207, 229)), "New Input", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(51, 51, 51)));
        GridBagConstraints gbc_slimSearchOptionsPanel = new GridBagConstraints();
        gbc_slimSearchOptionsPanel.insets = new Insets(5, 5, 5, 5);
        gbc_slimSearchOptionsPanel.fill = GridBagConstraints.BOTH;
        gbc_slimSearchOptionsPanel.gridx = 0;
        gbc_slimSearchOptionsPanel.gridy = 0;
        sLiMFinderPanel.add(slimSearchOptionsPanel, gbc_slimSearchOptionsPanel);

        GridBagLayout gbl_slimSearchOptionsPanel = new GridBagLayout();
        gbl_slimSearchOptionsPanel.columnWidths = new int[]{0, 0};
        gbl_slimSearchOptionsPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_slimSearchOptionsPanel.columnWeights = new double[]{1.0,
                Double.MIN_VALUE};
        gbl_slimSearchOptionsPanel.rowWeights = new double[]{1.0, 0.0, 1.0,
                Double.MIN_VALUE};
        slimSearchOptionsPanel.setLayout(gbl_slimSearchOptionsPanel);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        slimSearchOptionsPanel.add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel idLabel = new JLabel("Uniprot IDs:");
        GridBagConstraints gbc1_motifLabel = new GridBagConstraints();
        gbc1_motifLabel.anchor = GridBagConstraints.WEST;
        gbc1_motifLabel.insets = new Insets(0, 0, 5, 5);
        gbc1_motifLabel.gridx = 0;
        gbc1_motifLabel.gridy = 1;
        slimSearchOptionsPanel.add(idLabel, gbc1_motifLabel);

        runSLiMFinderButton = new JButton("Run SLiMFinder");
        GridBagConstraints gbc_runSLiMFinderButton = new GridBagConstraints();
        gbc_runSLiMFinderButton.anchor = GridBagConstraints.EAST;
        gbc_runSLiMFinderButton.insets = new Insets(0, 0, 0, 5);
        gbc_runSLiMFinderButton.gridx = 0;
        gbc_runSLiMFinderButton.gridy = 1;
        slimSearchOptionsPanel.add(runSLiMFinderButton, gbc_runSLiMFinderButton);

        uniprotTextArea = new JTextArea(4, 15);
        uniprotTextArea.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(uniprotTextArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setMinimumSize(new Dimension(15, 60));
        GridBagConstraints gbc1_textArea = new GridBagConstraints();
        gbc1_textArea.insets = new Insets(0, 0, 0, 5);
        gbc1_textArea.fill = GridBagConstraints.BOTH;
        gbc1_textArea.gridx = 0;
        gbc1_textArea.gridy = 2;
        slimSearchOptionsPanel.add(scroll, gbc1_textArea);

        JPanel pastRunPanel = new JPanel();
        pastRunPanel.setBorder(new TitledBorder(new LineBorder(
                new Color(184, 207, 229)), "Past Run", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(51, 51, 51)));
        GridBagConstraints gbc_pastRunPanel = new GridBagConstraints();
        gbc_pastRunPanel.insets = new Insets(5, 5, 5, 5);
        gbc_pastRunPanel.fill = GridBagConstraints.BOTH;
        gbc_pastRunPanel.gridx = 0;
        gbc_pastRunPanel.gridy = 1;
        sLiMFinderPanel.add(pastRunPanel, gbc_pastRunPanel);

        GridBagLayout gbl_pastRunPanel = new GridBagLayout();
        gbl_pastRunPanel.columnWidths = new int[]{0, 0};
        gbl_pastRunPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_pastRunPanel.columnWeights = new double[]{1.0,
                Double.MIN_VALUE};
        gbl_pastRunPanel.rowWeights = new double[]{1.0, 0.0, 1.0,
                Double.MIN_VALUE};
        pastRunPanel.setLayout(gbl_pastRunPanel);

        JLabel motifLabel = new JLabel("Run ID:");
        GridBagConstraints gbc_motifLabel = new GridBagConstraints();
        gbc_motifLabel.anchor = GridBagConstraints.WEST;
        gbc_motifLabel.insets = new Insets(0, 0, 5, 5);
        gbc_motifLabel.gridx = 0;
        gbc_motifLabel.gridy = 1;
        pastRunPanel.add(motifLabel, gbc_motifLabel);

        JButton idCheckButton = new JButton("Retrieve");
        GridBagConstraints gbc_checkLabel = new GridBagConstraints();
        gbc_checkLabel.anchor = GridBagConstraints.EAST;
        gbc_checkLabel.insets = new Insets(0, 0, 0, 5);
        gbc_checkLabel.gridx = 0;
        gbc_checkLabel.gridy = 1;
        pastRunPanel.add(idCheckButton, gbc_checkLabel);


        idTextArea = new JTextArea();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.insets = new Insets(0, 0, 0, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 2;
        pastRunPanel.add(idTextArea, gbc_textArea);

        idCheckButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (idTextArea.getText().length() > 6) {
                    String id = idTextArea.getText();
                    int ready = CommonMethods.checkReady(id, openBrowser);
                    if (ready == 1) { // ready
                        String url = "http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id;
                        if (CommonMethods.checkProgramsMatch(url, "SLiMFinder", openBrowser)) {
                            resultProcessing(id);
                        } else {
                            JOptionPane.showMessageDialog(null, "This Run ID was not from SLiMFinder.\n" +
                                    "Please use the original program to import this data.");
                        }
                    } else if (ready != -2) {
                        JOptionPane.showMessageDialog(null, "This ID is still being processed. Please check back later.");
                    }
                }
            }
        });


        runSLiMFinderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CyNetwork network = manager.getCurrentNetwork();

                // There are a set of IDs in the IDs box
                if (uniprotTextArea.getText().length() > 5) {
                    String input = uniprotTextArea.getText();
                    // Strings have to be comma+space delineated ONLY
                    List<String> ids = Arrays.asList(input.split(",\\s+|\\s+"));
                    RunSlimfinder slimfinder = new RunSlimfinder(network, null, ids, optionsPanel);
                    String url = slimfinder.getUrl();
                    String id = CommonMethods.getJobID(url).replaceAll("\\s+", "");
                    idTextArea.setText(id);
                    // Make sure the job is ready before analysis starts
                    int ready = CommonMethods.checkReady(id, openBrowser);
                    if (ready == 1) {
                        resultProcessing(id);
                    }
                // Get node IDs from the graph
                } else {
                    try {
                        List<CyNode> selected = new ArrayList<CyNode>();
                        selected.addAll(CyTableUtil.getNodesInState(network, "selected", true));
                        if (selected.size() > 1) {
                            RunSlimfinder slimfinder = new RunSlimfinder(network, selected, null, optionsPanel);
                            String url = slimfinder.getUrl();
                            String id = CommonMethods.getJobID(url).replaceAll("\\s+", "");
                            idTextArea.setText(id);
                            // Make sure the job is ready before analysis starts
                            int ready = CommonMethods.checkReady(id, openBrowser);
                            if (ready == 1) {
                                resultProcessing(id);
                            }
                        } else {
                            boolean fill = CommonMethods.noInputResponse();
                            if (fill) {
                                uniprotTextArea.setText("P32519,P35251,P35869,P51531,P16788,P24385,P29374,P29375,Q98178,"
                                        + "Q99683,Q99708,Q9R002,Q12931,Q13029,Q13107,Q8UYK9,Q9WKM8,Q9WIJ4,Q9Y6B2,P06847,"
                                        + "P13889,P14990,P15092,P03129,P03255,P05205,P06726,O39521,O75150,O75884,P03070");
                            }
                        }
                    } catch (Exception ex) {
                        boolean fill = CommonMethods.noInputResponse();
                        if (fill) {
                            uniprotTextArea.setText("P32519,P35251,P35869,P51531,P16788,P24385,P29374,P29375,Q98178,"
                                    + "Q99683,Q99708,Q9R002,Q12931,Q13029,Q13107,Q8UYK9,Q9WKM8,Q9WIJ4,Q9Y6B2,P06847,"
                                    + "P13889,P14990,P15092,P03129,P03255,P05205,P06726,O39521,O75150,O75884,P03070");
                        }
                    }
                }
            }
        });
    }

    private void resultProcessing(String id) {
        List<String> csvResults = CommonMethods.PrepareResults(
                "http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id, "&rest=main", openBrowser, id);
        if (csvResults != null) {
            displayResults(csvResults, id);
        }
    }

    /**
     * @desc - creates the results panels and alters/creates the cytoscape network as required.
     * @param csvResults  - processed results from the main page.
     * @param id -  run ID from the server.
     */
    private void displayResults(List<String> csvResults, final String id) {
        // Get OCC Results
        List<String> occResults = CommonMethods.PrepareResults(
                "http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id, "&rest=occ", openBrowser, id);

        // Get list of all node IDs from slimdb
        List<String> nodeIds = CommonMethods.getNodeIds("http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id
                + "&rest=slimdb");

        // Get graph edge data from upc outputs
        List<String> upc = CommonMethods.getUpcResults("http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id
                + "&rest=upc");

        // Create button to take users to the full results
        JButton fullResults = new JButton();
        fullResults.setText("Full results");
        fullResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBrowser.openURL("http://rest.slimsuite.unsw.edu.au/retrieve&jobid=" + id);
            }
        });

        // Create button to take users to the help page on github
        JButton help = new JButton();
        help.setText("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBrowser.openURL("https://github.com/slimsuite/SLiMScape/wiki/SLiMScape");
            }
        });

        // Create button to clear formatting
        JButton clearFormatting = new JButton();
        clearFormatting.setText("Clear formatting");
        clearFormatting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AlterGraph.resetNodeStyle(manager);
            }
        });

        JTable csv = CommonMethods.createCsvTable(csvResults);
        JTable occ = CommonMethods.createOccTable(occResults);
        csv.setAutoCreateRowSorter(true);
        occ.setAutoCreateRowSorter(true);

        // Obtain which nodes have which patterns in them
        Map<String, ArrayList<String>> occIds = new HashMap<String, ArrayList<String>>();
        for (int y=0; y<occ.getRowCount(); y++) {
            Object name = occ.getModel().getValueAt(y, 2);
            String nameString = String.valueOf(name);
            Object pattern = occ.getModel().getValueAt(y, 0);
            String patternString = String.valueOf(pattern);
            if (!occIds.containsKey(nameString)) {
                ArrayList<String> patt = new ArrayList<String>();
                patt.add(patternString);
                occIds.put(nameString, patt);
            } else {
                ArrayList<String> patt = occIds.get(nameString);
                patt.add(patternString);
                occIds.put(nameString, patt);
            }
        }

        // Obtain the patterns found
        ArrayList<String> patterns = new ArrayList<String>();
        for (int y=0; y<csv.getRowCount(); y++) {
            Object pattern = csv.getModel().getValueAt(y, 2);
            String patternString = String.valueOf(pattern);
            patterns.add(patternString);
        }

            String programName = runSLiMFinderButton.getText().split(" ")[1];
        programName = programName + ' ' + id;

        // Alter the graph
        new AlterGraph(programName, nodeIds, occIds, upc, patterns, manager, eventHelper, networkFactory, networkManager,
                networkViewFactory, networkViewManager, visualMappingManager, adapter);

        // Display the results in a panel
        JPanel resultsPane = new ResultsPanel(new JScrollPane(csv), new JScrollPane(occ), fullResults, help,
                clearFormatting, slimfinder, id);
        slimfinder.add(id, resultsPane);
    }

}
