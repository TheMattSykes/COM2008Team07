package Controllers;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.Student;
import Models.User;
import Models.Views;
import Views.AddStudent;
import Views.EditStudent;
import Views.RegistrarModules;
import Views.RegistrarView;

public class RegistrarSystemController extends Controller {
	
	private RegistrarView rv;
	private AddStudent as;
	private EditStudent es;
	private RegistrarModules rm;
	private Student selectedStudent;
	private DatabaseController dc;
	private Views currentView;
	private ArrayList<Integer> regNumbersInUse;
	private ArrayList<Module> originalModules;
	int totalCredits = 0;
	
	private Object[][] studentData;
	
	public RegistrarSystemController(User mainUser, RegistrarView rview) throws Exception {
		super(mainUser);
		
		rv = rview;
		
		dc = new DatabaseController();
		
		initDefaultView();
	}
	
	public User getUser() {
		return user;
	}
	
	public void initDefaultView() throws Exception {
		rv.setStudentsData(getStudentsData());
		rv.loadUI();
		currentView = Views.REGISTRARVIEW;

		JButton addButton = rv.getAddButton();
		JButton editButton = rv.getEditButton();
		JButton deleteButton = rv.getDeleteButton();
		JButton modulesButton = rv.getModulesButton();
		// Row selection listener
		rv.getTable().getSelectionModel().addListSelectionListener(e -> {
        	if (!editButton.isEnabled())
        		editButton.setEnabled(true);
        	if (!deleteButton.isEnabled())
        		deleteButton.setEnabled(true);
        	if (!modulesButton.isEnabled())
        		modulesButton.setEnabled(true);
		});
		
		// Action listener for Add Student button
		addButton.addActionListener(e -> {				
			try {
				changeView(Views.ADDSTUDENT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Edit Student Button
		editButton.addActionListener(e -> {
			try {
				JTable table = rv.getTable();
				selectedStudent = new Student();
				int row = table.getSelectedRow();
				selectedStudent.setCode((int)studentData[row][0]);
				selectedStudent.setTitle((String)studentData[row][1]);
				selectedStudent.setSecondName((String)studentData[row][2]);
				selectedStudent.setFirstName((String)studentData[row][3]);
				selectedStudent.setDegree((String)studentData[row][4]);
				selectedStudent.setEmail((String)studentData[row][5]);
				selectedStudent.setTutor((String)studentData[row][6]);
				selectedStudent.setPeriod((Character)studentData[row][7]);
				selectedStudent.setLevel((int)studentData[row][8]);
				selectedStudent.setRegistered((String)studentData[row][9]);
				changeView(Views.EDITSTUDENT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Delete Student button
		deleteButton.addActionListener(e -> {
			try {
				deleteStudent();
				changeView(Views.REGISTRARVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Add/Remove modules button
		modulesButton.addActionListener(e -> {				
			try {
				JTable table = rv.getTable();
				selectedStudent = new Student();
				int row = table.getSelectedRow();
				selectedStudent.setCode((int)studentData[row][0]);
				selectedStudent.setTitle((String)studentData[row][1]);
				selectedStudent.setSecondName((String)studentData[row][2]);
				selectedStudent.setFirstName((String)studentData[row][3]);
				selectedStudent.setDegree((String)studentData[row][4]);
				selectedStudent.setEmail((String)studentData[row][5]);
				selectedStudent.setTutor((String)studentData[row][6]);
				selectedStudent.setPeriod((Character)studentData[row][7]);
				selectedStudent.setLevel((int)studentData[row][8]);
				selectedStudent.setRegistered((String)studentData[row][9]);
				changeView(Views.REGISTRARMODULES);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	private void deleteStudent() throws Exception {
		JTable table = rv.getTable();
		
		int row = table.getSelectedRow();
		
		System.out.println("ROW: " + row);
		
		String regNumber = studentData[row][0].toString();
		
		String query = "DELETE FROM students WHERE reg_number = ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {regNumber, "false"});
		dc.executeQuery(query, values);
		
		System.out.println("UserID: " + regNumber);
	}

	public void initAddStudentView() throws Exception {
		if (as == null)
			as = new AddStudent(rv.getFrame());
		as.setAvailableDegrees(getAvailableDegrees());
		as.loadUI();
		currentView = Views.ADDSTUDENT;
		// Action listener for Back button
		as.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.REGISTRARVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Apply button
		as.getApplyButton().addActionListener(e -> {
			try {
				Student student = as.getNewStudent();

				if (student != null && student.isComplete()) {
					String email = student.getFirstName().substring(0, 1).toLowerCase()+student.getSecondName().toLowerCase();
					String query = "SELECT * FROM students WHERE email LIKE '"+email+"%'";
					ArrayList<String[]> values = new ArrayList<String[]>();
					//values.add(new String[] {student.getSecondName(), "true"});
					//values.add(new String[] {student.getFirstName(), "true"});
					ArrayList<String[]> results = dc.executeQuery(query, values);
					System.out.println("Email results size: "+results.size());
					
					email += (results.size()+1);
					email += "@snowbelle.ac.uk";
					student.setEmail(email);
					System.out.println("Email: "+email);
					
					Object[] options = {"Yes", "No"};
					int applyOption = JOptionPane.showOptionDialog(as.getFrame(), "Confirm adding "+student.getFirstName()+" "+
							student.getSecondName()+" to the students table in the database?", "Apply question", JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (applyOption == 0) {
						student.setCode(generateRandomReg());
						try {
							query = "INSERT INTO students VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?)";
							values = new ArrayList<String[]>();
							
							// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
							values.add(new String[] {Integer.toString(student.getRegNumber()), "false"});
							values.add(new String[] {student.getTitle(), "true"});
							values.add(new String[] {student.getSecondName(), "true"});
							values.add(new String[] {student.getFirstName(), "true"});
							values.add(new String[] {student.getDegree(), "true"});
							values.add(new String[] {student.getEmail(), "true"});
							values.add(new String[] {student.getTutor(), "true"});
							values.add(new String[] {Character.toString(student.getPeriod()), "true"});
							values.add(new String[] {Integer.toString(student.getLevel()), "false"});
							values.add(new String[] {student.getRegistered(), "true"});
							
							dc.executeQuery(query, values);
							changeView(Views.REGISTRARVIEW);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {
					JOptionPane optionPane = new JOptionPane("Please make sure all the values have been filled in correctly."+
															 "\n(Names and Tutor have a maximum length of 50 each.)", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Failure");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane optionPane = new JOptionPane("Error connecting to dabatase.", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
	}
	
	public void initEditStudentView() throws Exception {
		if (es == null)
			es = new EditStudent(rv.getFrame());
		
		es.setStudent(selectedStudent);
		es.setAvailableDegrees(getAvailableDegrees());
		es.loadUI();
		currentView = Views.EDITSTUDENT;
		// Action listener for Back button
		es.getBackButton().addActionListener(e -> {				
			try {
				changeView(Views.REGISTRARVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// Action listener for Apply button
		es.getApplyButton().addActionListener(e -> {
			try {
				Student student = es.getNewStudent();
				if (student != null && student.isComplete()) {										
					Object[] options = {"Yes", "No"};
					int applyOption = JOptionPane.showOptionDialog(es.getFrame(), "Confirm updating "+student.getFirstName()+" "+
							student.getSecondName()+" in the students table in the database?", "Apply question", JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (applyOption == 0) {
						try {
							String query = "UPDATE students SET title = ?, surname = ?, forename = ?, degree = ?, "
										 + "tutor = ?, period = ?, level = ?, registered = ? WHERE reg_number = ?;";
							ArrayList<String[]> values = new ArrayList<String[]>();
							
							// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
							values.add(new String[] {student.getTitle(), "true"});
							values.add(new String[] {student.getSecondName(), "true"});
							values.add(new String[] {student.getFirstName(), "true"});
							values.add(new String[] {student.getDegree(), "true"});
							values.add(new String[] {student.getTutor(), "true"});
							values.add(new String[] {Character.toString(student.getPeriod()), "true"});
							values.add(new String[] {Integer.toString(student.getLevel()), "true"});
							values.add(new String[] {student.getRegistered(), "true"});
							values.add(new String[] {Integer.toString(student.getRegNumber()), "true"});
							
							dc.executeQuery(query, values);
							changeView(Views.REGISTRARVIEW);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {
					JOptionPane optionPane = new JOptionPane("Please make sure all the values have been filled in correctly."+
															 "\n(Names and Tutor have a maximum length of 50 each.)", JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("Failure");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane optionPane = new JOptionPane("Error connecting to dabatase.", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
	}
	
	public void initRegistrarModulesView() throws Exception {
		if (rm == null)
			rm = new RegistrarModules(rv.getFrame());
		
		rm.setStudent(selectedStudent);
		ArrayList<Module> enrolledModules = getEnrolledModules();
		rm.setCurrentModules(enrolledModules);
		JLabel creditsLabel = rm.getCreditsLabel();
		ArrayList<Module> availableModules = new ArrayList<Module>(getAvailableModules());
		// Remove duplicate modules (already enrolled)
		for (Module enrolledModule : enrolledModules) {
			availableModules.removeIf(module -> module.getCode().equals(enrolledModule.getCode()));
		}
		rm.setAvailableModules(availableModules);
		rm.loadUI();
		currentView = Views.REGISTRARMODULES;
		
		JButton removeButton = rm.getRemoveModuleButton();
		JTable currentModulesTable = rm.getCurrentModulesTable();
		// Row selection listener for currentModulesTable
		currentModulesTable.getSelectionModel().addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting() && currentModulesTable.getSelectedRow() > -1) {
				// A module has to be incomplete and optional, for it to be removable
	        	if (!enrolledModules.get(currentModulesTable.getSelectedRow()).isCore() &&
	        		enrolledModules.get(currentModulesTable.getSelectedRow()).getMaxGrade() == Grades.UNDEFINED) {
	        		removeButton.setEnabled(true);
	        	} else {
	        		removeButton.setEnabled(false);
	        	}
			}
		});
		
		DefaultTableModel currentModulesTableModel = rm.getCurrentModulesTableModel();
		DefaultTableModel availableModulesTableModel = rm.getAvailableModulesTableModel();
		
		// Action listener for remove module button
		removeButton.addActionListener(e -> {
			availableModulesTableModel.addRow(new Object[] {enrolledModules.get(currentModulesTable.getSelectedRow()).getCode(),
					enrolledModules.get(currentModulesTable.getSelectedRow()).getName(),
					enrolledModules.get(currentModulesTable.getSelectedRow()).getCredits(),
					enrolledModules.get(currentModulesTable.getSelectedRow()).getLevel(),
					enrolledModules.get(currentModulesTable.getSelectedRow()).isCore()});
			Module module = enrolledModules.get(currentModulesTable.getSelectedRow());
			if (module.getLevel() == selectedStudent.getLevel()) {
				totalCredits -= module.getCredits();
				String creditsLabelText ="<html>Total number of credits for level "+selectedStudent.getLevel()+" are: ";
				if (((selectedStudent.getLevel() > 0 || selectedStudent.getLevel() < 5) && totalCredits != 120) ||
					(selectedStudent.getLevel() == 6 && totalCredits != 180)) {
					creditsLabelText += totalCredits+"<br/><font color='red'>The total number of credits for this year has to be ";
					if (selectedStudent.getLevel() > 0 && selectedStudent.getLevel() < 5) {
						creditsLabelText += "120.";
					} else if (selectedStudent.getLevel() == 6) {
						creditsLabelText += "180.";
					}
					creditsLabelText += "</font></html>";
					creditsLabel.setText(creditsLabelText);
					rm.getApplyButton().setEnabled(false);
				} else {
					creditsLabelText += totalCredits+"</html>";
					creditsLabel.setText(creditsLabelText);
					rm.getApplyButton().setEnabled(true);
				}
			}
			availableModules.add(module);
			enrolledModules.remove(currentModulesTable.getSelectedRow());
			currentModulesTableModel.removeRow(currentModulesTable.getSelectedRow());
			removeButton.setEnabled(false);
		});
		
		JButton addButton = rm.getAddModuleButton();
		JTable availableModulesTable = rm.getAvailableModulesTable();
		// Row selection listener for availableModulesTable
		availableModulesTable.getSelectionModel().addListSelectionListener(e -> {
        	if (!addButton.isEnabled() && !e.getValueIsAdjusting() && availableModulesTable.getSelectedRow() > -1)
        		addButton.setEnabled(true);
		});
		
		// Action listener for add module button
		addButton.addActionListener(e -> {
			currentModulesTableModel.addRow(new Object[] {availableModules.get(availableModulesTable.getSelectedRow()).getCode(),
					availableModules.get(availableModulesTable.getSelectedRow()).getName(),
					Grades.UNDEFINED, availableModules.get(availableModulesTable.getSelectedRow()).getCredits(),
					availableModules.get(availableModulesTable.getSelectedRow()).getLevel(),
					availableModules.get(availableModulesTable.getSelectedRow()).isCore()});
			Module module = availableModules.get(availableModulesTable.getSelectedRow());
			module.setDepartment(selectedStudent.getDegree());
			module.setGrades(new Grades[] {Grades.UNDEFINED, null});
			module.setScores(new int[] {0, 0});
			enrolledModules.add(module);
			totalCredits += module.getCredits();
			String creditsLabelText ="<html>Total number of credits for level "+selectedStudent.getLevel()+" are: ";
			if (((selectedStudent.getLevel() > 0 || selectedStudent.getLevel() < 5) && totalCredits != 120) ||
				(selectedStudent.getLevel() == 6 && totalCredits != 180)) {
				creditsLabelText += totalCredits+"<br/><font color='red'>The total number of credits for this year has to be ";
				if (selectedStudent.getLevel() > 0 && selectedStudent.getLevel() < 5) {
					creditsLabelText += "120.";
				} else if (selectedStudent.getLevel() == 6) {
					creditsLabelText += "180.";
				}
				creditsLabelText += "</font></html>";
				creditsLabel.setText(creditsLabelText);
				rm.getApplyButton().setEnabled(false);
			} else {
				creditsLabelText += totalCredits+"</html>";
				creditsLabel.setText(creditsLabelText);
				rm.getApplyButton().setEnabled(true);
			}
			availableModules.remove(availableModulesTable.getSelectedRow());
			availableModulesTableModel.removeRow(availableModulesTable.getSelectedRow());
			addButton.setEnabled(false);
		});
		
		// Action listener for Apply button
		rm.getApplyButton().addActionListener(e -> {
			try {
				Object[] options = {"Yes", "No"};
				int applyOption = JOptionPane.showOptionDialog(rm.getFrame(), "Confirm updating "+selectedStudent.getFirstName()+" "+
						selectedStudent.getSecondName()+"'s modules in the database?", "Apply question", JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (applyOption == 0) {
					ArrayList<Module> addables = new ArrayList<Module>(enrolledModules);
					addables.removeAll(originalModules);
					ArrayList<Module> removables = new ArrayList<Module>(originalModules);
					removables.removeAll(enrolledModules);
					try {
						// Delete modules
						if (removables.size() > 0) {
							String query = "DELETE FROM enrolled WHERE reg_number = ? AND  module_code IN (?";
							ArrayList<String[]> values = new ArrayList<String[]>();
							// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
							values.add(new String[] {Integer.toString(selectedStudent.getRegNumber()), "false"});
							values.add(new String[] {removables.get(0).getCode(),"true"});
							
							for (Module removable : removables) {
								query += ", ?";
								values.add(new String[] {removable.getCode(),"true"});
							}
							query += ")";							
							
							dc.executeQuery(query, values);
						}
						
						// Add modules
						if (addables.size() > 0) {
							String query = "INSERT INTO enrolled (reg_number, module_code, grade1, grade2) VALUES (?, ?, NULL, NULL)";
							ArrayList<String[]> values = new ArrayList<String[]>();
							// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
							values.add(new String[] {Integer.toString(selectedStudent.getRegNumber()), "false"});
							values.add(new String[] {addables.get(0).getCode(),"true"});
							
							for (Module addable : addables) {
								query += ", (?, ?, NULL, NULL)";
								values.add(new String[] {Integer.toString(selectedStudent.getRegNumber()), "false"});
								values.add(new String[] {addable.getCode(),"true"});
							}						
							
							dc.executeQuery(query, values);
						}
						changeView(Views.REGISTRARVIEW);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane optionPane = new JOptionPane("Error connecting to dabatase.", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
			}
		});
		
		// Action listener for Back button
		rm.getBackButton().addActionListener(e -> {
			try {
				changeView(Views.REGISTRARVIEW);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	// Changes to the specified view
	public void changeView(Views changeTo) throws Exception {
		if (currentView == Views.REGISTRARVIEW) {
			rv.removeUI();
		} else if (currentView == Views.ADDSTUDENT) {
			as.removeUI();
		} else if (currentView == Views.EDITSTUDENT) {
			es.removeUI();
		} else if (currentView == Views.REGISTRARMODULES) {
			rm.removeUI();
		}
		
		if (changeTo == Views.REGISTRARVIEW) {
			initDefaultView();
		} else if (changeTo == Views.ADDSTUDENT) {
			initAddStudentView();
		} else if (changeTo == Views.EDITSTUDENT) {
			initEditStudentView();
		} else if (changeTo == Views.REGISTRARMODULES) {
			initRegistrarModulesView();
		}
	}
	
	// Generates a random Reg. number, that is not yet used in the students table of the DB
	public int generateRandomReg() {
		int minReg = 10000000;
		int maxReg = 99999999;
		int regNumber = 0;
		// Generate a random number between min and max, until a suitable one is generated (number of tries depends on number of rows in the table)
		while(regNumbersInUse.contains(regNumber) || regNumber < minReg || regNumber > maxReg) {
			regNumber = minReg + new Random().nextInt(maxReg-minReg+1);
		}
		return regNumber;
	}
	
	// Gets the data of all the students currently in the database
	public Object[][] getStudentsData() throws Exception {
		
		String query = "SELECT * FROM students LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		
		ArrayList<Student> students = new ArrayList<Student>();
		regNumbersInUse = new ArrayList<Integer>();
		
		for (int i = 0; i < results.size(); i++) {
			students.add(new Student(Integer.parseInt(results.get(i)[0]),results.get(i)[1],results.get(i)[2],results.get(i)[3],results.get(i)[4], results.get(i)[5], results.get(i)[6], results.get(i)[7].charAt(0), Integer.parseInt(results.get(i)[8]), results.get(i)[10]));
			regNumbersInUse.add(Integer.parseInt(results.get(i)[0]));
		}
		
		Object[][] data = new Object[results.size()][10];
		
		int row = 0;
		for (Student student : students) {
			data[row][0] = student.getRegNumber();
			data[row][1] = student.getTitle();
			data[row][2] = student.getFirstName();
			data[row][3] = student.getSecondName();
			data[row][4] = student.getDegree();
			data[row][5] = student.getEmail();
			data[row][6] = student.getTutor();
			data[row][7] = student.getPeriod();
			data[row][8] = student.getLevel();
			data[row][9] = student.getRegistered();
			
			row++;
		}
		
		studentData = data;
		
		return data;
	}
	
	public String[] getAvailableDegrees() throws Exception {
		String query = "SELECT degree_code FROM degrees LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", "false"});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		String[] availableDegrees = new String[results.size()];
		
		for (int i = 0; i < results.size(); i++) {
			availableDegrees[i] = results.get(i)[0];
		}
		
		return availableDegrees;
	}
	
	public ArrayList<Module> getEnrolledModules() throws Exception {		
		String query = String.format("SELECT enrolled.module_code, enrolled.grade1, enrolled.grade2, modules.module_name, modules.credits, modules.teaching_period," + 
				" modules.graduation_level, approval.core, approval.level FROM enrolled INNER JOIN modules ON enrolled.module_code=modules.module_code INNER JOIN" +
				" approval ON enrolled.module_code=approval.module_code WHERE enrolled.reg_number = ? AND approval.degree_code = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[]{Integer.toString(selectedStudent.getRegNumber()),"false"});		
		values.add(new String[]{selectedStudent.getDegree(), "true"});
		
		ArrayList<String[]> results = dc.executeQuery(query,values);
		ArrayList<Module> modules = new ArrayList<Module>();
		
		totalCredits = 0;
		
		if (results.size() > 0) {
			
			for (String[] result : results) {
				Module newModule = new Module();
				
				String code = result[0];
				newModule.setCode(code);
				
				int[] studentResults = new int[2];
				Grades[] studentGrades = new Grades[2];
				
				if (result[1] != null) {
					studentResults[0] = (int) Float.parseFloat(result[1]);
					
					if (studentResults[0] >= 40) {
						studentGrades[0] = Grades.PASS;
					} else {
						studentGrades[0] = Grades.FAIL;
					}
				} else {
					studentResults[0] = 0;
					studentGrades[0] = Grades.UNDEFINED;
				}
				
				if (result[2] != null) {
					studentResults[1] = (int) Float.parseFloat(result[2]);
					
					if (studentResults[1] >= 40) {
						studentGrades[1] = Grades.PASS;
					} else {
						studentGrades[1] = Grades.FAIL;
					}
				} else {
					studentResults[1] = 0;
					studentGrades[1] = Grades.UNDEFINED;
				}
				
				newModule.setScores(studentResults);
				newModule.setGrades(studentGrades);
				
				
				newModule.setName(result[3]);
				newModule.setCredits(Integer.parseInt(result[4]));
				newModule.setTeachingPeriod(result[5]);
				String core;
				if (result[7] == "0")
					core = "false";
				else
					core = "true";
				newModule.setCore(core);
				newModule.setLevel(Integer.parseInt(result[8]));
				newModule.setType(GraduateType.valueOf(result[6].toUpperCase()));
				
				if (newModule.getLevel() == selectedStudent.getLevel())
					totalCredits += newModule.getCredits();
				
				modules.add(newModule);
			}
		}
		
		String creditsLabelText ="<html>Total number of credits for level "+selectedStudent.getLevel()+" are: ";
		JLabel creditsLabel = rm.getCreditsLabel();
		if (((selectedStudent.getLevel() > 0 || selectedStudent.getLevel() < 5) && totalCredits != 120) ||
			(selectedStudent.getLevel() == 6 && totalCredits != 180)) {
			creditsLabelText += totalCredits+"<br/><font color='red'>The total number of credits for this year has to be ";
			if (selectedStudent.getLevel() > 0 && selectedStudent.getLevel() < 5) {
				creditsLabelText += "120.";
			} else if (selectedStudent.getLevel() == 6) {
				creditsLabelText += "180.";
			}
			creditsLabelText += "</font></html>";
			creditsLabel.setText(creditsLabelText);
			rm.getApplyButton().setEnabled(false);
		} else {
			creditsLabelText += totalCredits+"</html>";
			creditsLabel.setText(creditsLabelText);
			rm.getApplyButton().setEnabled(true);
		}
		
		originalModules = new ArrayList<Module>(modules);
		return modules;
	}
	
	public ArrayList<Module> getAvailableModules() throws Exception {
		String query = String.format("SELECT approval.module_code, approval.core, approval.level, modules.module_name, modules.credits, modules.teaching_period," + 
				" modules.graduation_level FROM approval INNER JOIN modules ON approval.module_code=modules.module_code WHERE approval.degree_code = ? AND approval.level = ?");
		
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[]{selectedStudent.getDegree(),"true"});
		values.add(new String[]{Integer.toString(selectedStudent.getLevel()),"false"});
		
		ArrayList<String[]> results = dc.executeQuery(query,values);
		ArrayList<Module> modules = new ArrayList<Module>();
		
		if (results.size() > 0) {
					
			for (String[] result : results) {
				Module newModule = new Module();
				
				String code = result[0];
				newModule.setCode(code);
				String core;
				if (result[1] == "0")
					core = "false";
				else
					core = "true";
				newModule.setCore(core);
				newModule.setLevel(Integer.parseInt(result[2]));
				newModule.setName(result[3]);
				newModule.setCredits(Integer.parseInt(result[4]));
				newModule.setTeachingPeriod(result[5]);
				newModule.setType(GraduateType.valueOf(result[6].toUpperCase()));
				
				modules.add(newModule);
			}
			
		}
		
		return modules;
	}
	
	public int getMax(int[] scores) {
		int max = 0;
		
		for (int score : scores) {
			if (score > max) {
				max = score;
			}
		}
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[]{selectedStudent.getDegree(),"true"});
		values.add(new String[]{Integer.toString(selectedStudent.getLevel()),"false"});
		
		return max;
	}
	
	public Classification calculateClass(GraduateType type, Module[] mods) {
		
		float[] levelTotals = new float[4];
		float postGradTotal = 0;
		
		Boolean fourYearCourse = false;
		
		int yearCredits = 0;
		
		// Determine how many credits are in a year for graduate type
		if (type == GraduateType.UNDERGRADUATE) {
			yearCredits = 120;
		} else {
			yearCredits = 180;
		}
		
		// Get all module scores by level
		for (Module mod : mods) {
			int level = mod.getLevel();
			int[] scores = mod.getScores();
			int score = getMax(scores);
			int credits = mod.getCredits();
			
			if (level == 4) {
				fourYearCourse = true;
			}
			
			float weightedScore = (((float)credits / (float)yearCredits) * (float)score) * 100;
			
			if (type == GraduateType.UNDERGRADUATE) {
				levelTotals[level-1] += weightedScore;
			} else {
				postGradTotal += weightedScore;
			}
		}
		
		float finalValue = 0;
		
		if (type == GraduateType.UNDERGRADUATE) {
			
			if (!fourYearCourse) {
				finalValue = ( ((1/3)*levelTotals[1]) + ((2/3)*levelTotals[2]) );
			} else {
				finalValue = ( ((1/5)*levelTotals[1]) + ((2/5)*levelTotals[2]) + ((2/5)*levelTotals[3]) );
			}
			
			if (finalValue < 39.5) {
				return Classification.FAIL;
			} else if (finalValue >= 39.5 && finalValue < 44.5) {
				return Classification.PASS;
			} else if (finalValue >= 44.5 && finalValue < 49.5) {
				return Classification.THIRD;
			} else if (finalValue >= 49.5 && finalValue < 59.5) {
				return Classification.LOWER_SECOND;
			} else if (finalValue >= 59.5 && finalValue < 69.5) {
				return Classification.UPPER_SECOND;
			} else {
				return Classification.FIRST;
			}
		} else {
			finalValue = postGradTotal;
			
			if (finalValue < 49.5) {
				return Classification.FAIL;
			} else if (finalValue >= 49.5 && finalValue < 59.5) {
				return Classification.PASS;
			} else if (finalValue >= 59.5 && finalValue < 69.5) {
				return Classification.MERIT;
			} else {
				return Classification.DISTINCTION;
			}
		}
	}
	
	public void removeAllUI() {
		if (rv != null)
			rv.removeUI();
		if (as != null)
			as.removeUI();
		if (es != null)
			es.removeUI();
		if (rm != null)
			rm.removeUI();
	}
}
