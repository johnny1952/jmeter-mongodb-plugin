package com.jolestar.jmeter.mongodb.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

import com.jolestar.jmeter.mongodb.MongoDBConfigBeanInfo;
import com.jolestar.jmeter.mongodb.MongoDBResultSaver;

/**
 * Create a ResultSaver test element, which saves the sample information in set
 * of files
 *
 */
public class MongoDBResultSaverGui extends AbstractListenerGui implements Clearable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 9150131463742788845L;

	private JTextField mongoConfigName;
	private JTextField mongoCollectionName;
	private JTextField resultVarName;
	private JCheckBox successOnly;
	private JCheckBox errorsOnly;
	private JTextField objectIdName;
	private JTextField objectIdVarName;
	
	private static final String PROP_DISPLAY_NAME = "displayName";
	private static final String PROP_SHORT_DESCRIPTION = "shortDescription";
	
	public static final String DEFAULT_MONGO_DB_COLLECTION = "jmeter";
    
	/**
	 * resourceBundle need to be static. because getStaticLabel method be called at super() construct. 
	 */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(
    		MongoDBResultSaverGui.class.getName() + "Resources");

    public MongoDBResultSaverGui() {
        super();
        init();
    }

    /**
     * @see org.apache.jmeter.gui.JMeterGUIComponent#getStaticLabel()
     */
    @Override
    public String getLabelResource() {
        return null;
    }
    
    

    @Override
	public String getStaticLabel() {
		return resourceBundle.getString(PROP_DISPLAY_NAME);
	}

	/**
     * @see org.apache.jmeter.gui.JMeterGUIComponent#configure(TestElement)
     */
    @Override
    public void configure(TestElement el) {
        super.configure(el);
       
        this.mongoConfigName.setText(el.getPropertyAsString(MongoDBResultSaver.FIELD_MONGODB_CONFIG_NAME));
        this.mongoCollectionName.setText(el.getPropertyAsString(MongoDBResultSaver.FIELD_MONGODB_COLLECTION_NAME));
        this.resultVarName.setText(el.getPropertyAsString(MongoDBResultSaver.FIELD_RESULT_VAR_NAME));
        this.objectIdName.setText(el.getPropertyAsString(MongoDBResultSaver.FIELD_OBJECT_ID_NAME));
        this.objectIdVarName.setText(el.getPropertyAsString(MongoDBResultSaver.FIELD_OBJECT_ID_VAR_NAME));
        this.errorsOnly.setSelected(el.getPropertyAsBoolean(MongoDBResultSaver.FIELD_ERRORS_ONLY, false));
        this.successOnly.setSelected(el.getPropertyAsBoolean(MongoDBResultSaver.FIELD_SUCCESS_ONLY, true));
    }

    /**
     * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
     */
    @Override
    public TestElement createTestElement() {
    	MongoDBResultSaver resultSaver = new MongoDBResultSaver();
        modifyTestElement(resultSaver);
        return resultSaver;
    }

    /**
     * Modifies a given TestElement to mirror the data in the gui components.
     *
     * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
     */
    @Override
    public void modifyTestElement(TestElement te) {
        super.configureTestElement(te);
        te.setProperty(MongoDBResultSaver.FIELD_MONGODB_CONFIG_NAME, this.mongoConfigName.getText());
        te.setProperty(MongoDBResultSaver.FIELD_MONGODB_COLLECTION_NAME,this.mongoCollectionName.getText());
        te.setProperty(MongoDBResultSaver.FIELD_RESULT_VAR_NAME,this.resultVarName.getText());
        te.setProperty(MongoDBResultSaver.FIELD_SUCCESS_ONLY, this.successOnly.isSelected());
        te.setProperty(MongoDBResultSaver.FIELD_ERRORS_ONLY, this.errorsOnly.isSelected());
        te.setProperty(MongoDBResultSaver.FIELD_OBJECT_ID_NAME, this.objectIdName.getText());
        te.setProperty(MongoDBResultSaver.FIELD_OBJECT_ID_VAR_NAME, this.objectIdVarName.getText());
    }

    /**
     * Implements JMeterGUIComponent.clearGui
     */
    @Override
    public void clearGui() {
        super.clearGui();
        mongoConfigName.setText(MongoDBConfigBeanInfo.Field.varName.defaultValue().toString());
        mongoCollectionName.setText(DEFAULT_MONGO_DB_COLLECTION);
        this.resultVarName.setText("");
        this.successOnly.setSelected(true);
        this.errorsOnly.setSelected(false);
        this.objectIdName.setText("");
        this.objectIdVarName.setText("");
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        Box box = Box.createVerticalBox();
        box.add(makeTitlePanel());
        box.add(createPanel());      
        add(box, BorderLayout.NORTH);
    }

    private JPanel createPanel()
    {
    	JPanel panel = new JPanel(new GridLayout(8,2));
    	String labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_MONGODB_CONFIG_NAME+"."+PROP_DISPLAY_NAME)+":";
        JLabel label = new JLabel(labelStr); 

        mongoConfigName = new JTextField(10);
        mongoConfigName.setName(MongoDBResultSaver.FIELD_MONGODB_CONFIG_NAME);
        
        String tipName = MongoDBResultSaver.FIELD_MONGODB_CONFIG_NAME+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	mongoConfigName.setToolTipText(tip);
        }
        
        label.setLabelFor(mongoConfigName);
        
        panel.add(label);
        panel.add(mongoConfigName);
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_MONGODB_COLLECTION_NAME+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        mongoCollectionName = new JTextField(10);
        mongoCollectionName.setName(MongoDBResultSaver.FIELD_MONGODB_COLLECTION_NAME);
        tipName = MongoDBResultSaver.FIELD_MONGODB_COLLECTION_NAME+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	mongoCollectionName.setToolTipText(tip);
        }
        
        label.setLabelFor(mongoCollectionName);
 
        panel.add(label);
        panel.add(mongoCollectionName);
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_RESULT_VAR_NAME+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        resultVarName = new JTextField(10);
        resultVarName.setName(MongoDBResultSaver.FIELD_RESULT_VAR_NAME);
        tipName = MongoDBResultSaver.FIELD_RESULT_VAR_NAME+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	resultVarName.setToolTipText(tip);
        }
        
        label.setLabelFor(resultVarName);
 
        panel.add(label);
        panel.add(resultVarName);
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_OBJECT_ID_NAME+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        objectIdName = new JTextField(10);
        objectIdName.setName(MongoDBResultSaver.FIELD_OBJECT_ID_NAME);
        tipName = MongoDBResultSaver.FIELD_OBJECT_ID_NAME+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	objectIdName.setToolTipText(tip);
        }
        
        label.setLabelFor(objectIdName);
 
        panel.add(label);
        panel.add(objectIdName);
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_OBJECT_ID_VAR_NAME+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        objectIdVarName = new JTextField(10);
        objectIdVarName.setName(MongoDBResultSaver.FIELD_OBJECT_ID_VAR_NAME);
        tipName = MongoDBResultSaver.FIELD_OBJECT_ID_VAR_NAME+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	objectIdVarName.setToolTipText(tip);
        }
        
        label.setLabelFor(objectIdVarName);
 
        panel.add(label);
        panel.add(objectIdVarName);
        
        panel.add(new Label("*"));
        panel.add(new Label(resourceBundle.getString("objectIdNote")));
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_SUCCESS_ONLY+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        successOnly = new JCheckBox();
        successOnly.setName(MongoDBResultSaver.FIELD_SUCCESS_ONLY);
        tipName = MongoDBResultSaver.FIELD_SUCCESS_ONLY+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	successOnly.setToolTipText(tip);
        }
        
        label.setLabelFor(successOnly);
 
        panel.add(label);
        panel.add(successOnly);
        
        
        labelStr = resourceBundle.getString(MongoDBResultSaver.FIELD_ERRORS_ONLY+"."+PROP_DISPLAY_NAME)+":";
        label = new JLabel(labelStr); 
        errorsOnly = new JCheckBox();
        errorsOnly.setName(MongoDBResultSaver.FIELD_ERRORS_ONLY);
        tipName = MongoDBResultSaver.FIELD_SUCCESS_ONLY+"."+PROP_SHORT_DESCRIPTION;
        if(resourceBundle.containsKey(tipName)){
        	String tip = resourceBundle.getString(tipName);
        	errorsOnly.setToolTipText(tip);
        }
        
        label.setLabelFor(errorsOnly);
 
        panel.add(label);
        panel.add(errorsOnly);

        return panel;
    }


    @Override
    public void clearData() {
    }

}
