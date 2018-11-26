package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Models.Classification;
import Models.GraduateType;
import Models.Module;
import Models.Student;
import Models.User;
import Models.Views;
import Views.AddStudent;
import Views.EditStudent;
import Views.RegistrarView;

public class RegistrarSystemController extends Controller {
	
	User user;
	RegistrarView rv;
	AddStudent as;
	EditStudent es;
	Student selectedStudent;
	DatabaseController dc = new DatabaseController();
	private Views currentView;
	ArrayList<Integer> regNumbersInUse;
	
	private Object[][] studentData;
	
	public RegistrarSystemController(User mainUser, RegistrarView rview) throws Exception {
		user = mainUser;
		rv = rview;
		
		initDefaultView();
	}
	
	public User getUser() {
		return user;
	}
	
	public static void main(String[] args) {
		// Main stuff
	}
	
	
	public void initController() {		
		
	}
	
	public void initDefaultView() throws Exception {
		rv.setStudentsData(getStudentsData());
		rv.loadUI();
		currentView = Views.REGISTRARVIEW;
		// Action listener for Add Student button
		rv.getAddButton().addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					try {
						changeView(Views.ADDSTUDENT);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
		System.out.println("Hello there!");
		
		rv.getDeleteButton().addActionListener(e -> {
			System.out.println("Deletey Delete!");
			try {
				deleteStudent();
				changeView(Views.REGISTRARVIEW);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		rv.getEditButton().addActionListener(e -> {
			try {
				JTable table = rv.getTable();
				selectedStudent = new Student();
				selectedStudent.setCode((int)studentData[table.getSelectedRow()][0]);
				selectedStudent.setTitle((String)studentData[table.getSelectedRow()][1]);
				selectedStudent.setFirstName((String)studentData[table.getSelectedRow()][2]);
				selectedStudent.setSecondName((String)studentData[table.getSelectedRow()][3]);
				selectedStudent.setDegree((String)studentData[table.getSelectedRow()][4]);
				selectedStudent.setEmail((String)studentData[table.getSelectedRow()][5]);
				selectedStudent.setTutor((String)studentData[table.getSelectedRow()][6]);
				selectedStudent.setPeriod((Character)studentData[table.getSelectedRow()][7]);
				selectedStudent.setLevel((int)studentData[table.getSelectedRow()][8]);
				changeView(Views.EDITSTUDENT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		// JTable table = rv.getTable();
		
		// table.getSelectionModel().addListSelectionListener(e -> tableActions());
	}
	
	private void deleteStudent() throws Exception {
		JTable table = rv.getTable();
		
		int row = table.getSelectedRow();
		
		System.out.println("ROW: " + row);
		
		String regNumber = studentData[row][0].toString();
		
		String query = "DELETE FROM students WHERE reg_number = ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {regNumber, "0"});
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
		as.getBackButton().addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					try {
						changeView(Views.REGISTRARVIEW);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
		// Action listener for Apply button
		as.getApplyButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
										query = "INSERT INTO students VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";
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
					}
				}
			);
	}
	
	public void initEditStudentView() throws Exception {
		if (es == null) {
			es = new EditStudent(rv.getFrame(), selectedStudent);
		} else {
			es.setStudent(selectedStudent);
		}
		es.setAvailableDegrees(getAvailableDegrees());
		es.loadUI();
		currentView = Views.EDITSTUDENT;
		// Action listener for Back button
		es.getBackButton().addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					try {
						changeView(Views.REGISTRARVIEW);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
		// Action listener for Apply button
		es.getApplyButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
													 + "tutor = ?, period = ?, level = ? WHERE reg_number = ?";
										ArrayList<String[]> values = new ArrayList<String[]>();
										
										// Each value String[] has (1) the data, (2) boolean, which denotes whether it is a string
										values.add(new String[] {student.getTitle(), "true"});
										values.add(new String[] {student.getSecondName(), "true"});
										values.add(new String[] {student.getFirstName(), "true"});
										values.add(new String[] {student.getDegree(), "true"});
										values.add(new String[] {student.getTutor(), "true"});
										values.add(new String[] {Character.toString(student.getPeriod()), "true"});
										values.add(new String[] {Integer.toString(student.getLevel()), "false"});
										values.add(new String[] {Integer.toString(student.getRegNumber()), "false"});
										
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
					}
				}
			);
	}
	
	// Changes to the specified view
	public void changeView(Views changeTo) throws Exception {
		if (currentView == Views.REGISTRARVIEW) {
			rv.removeUI();
		} else if (currentView == Views.ADDSTUDENT) {
			as.removeUI();
		} else if (currentView == Views.EDITSTUDENT) {
			es.removeUI();
		}
		
		if (changeTo == Views.REGISTRARVIEW) {
			initDefaultView();
		} else if (changeTo == Views.ADDSTUDENT) {
			initAddStudentView();
		} else if (changeTo == Views.EDITSTUDENT) {
			initEditStudentView();
		}
	}
	
	// Generates a random reg. number, that is not yet used in the students table of the DB
	public int generateRandomReg() {
		int minReg = 10000000;
		int maxReg = 99999999;
		int regNumber = minReg + new Random().nextInt(maxReg-minReg+1);
		while(regNumbersInUse.contains(regNumber)) {
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
			students.add(new Student(Integer.parseInt(results.get(i)[0]),results.get(i)[1],results.get(i)[2],results.get(i)[3],results.get(i)[4], results.get(i)[5], results.get(i)[6], results.get(i)[7].charAt(0), Integer.parseInt(results.get(i)[8])));
			regNumbersInUse.add(Integer.parseInt(results.get(i)[0]));
		}
		
		Object[][] data = new Object[results.size()][9];
		
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
			
			row++;
		}
		
		studentData = data;
		
		return data;
	}
	
	public String[] getAvailableDegrees() throws Exception {
		String query = "SELECT degree_code FROM degrees LIMIT ?";
		ArrayList<String[]> values = new ArrayList<String[]>();
		values.add(new String[] {"1000", ""});
		ArrayList<String[]> results = dc.executeQuery(query, values);
		String[] availableDegrees = new String[results.size()];
		
		for (int i = 0; i < results.size(); i++) {
			availableDegrees[i] = results.get(i)[0];
		}
		
		return availableDegrees;
	}
	
	public int getMax(int[] scores) {
		int max = 0;
		
		for (int score : scores) {
			if (score > max) {
				max = score;
			}
		}
		
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
}
