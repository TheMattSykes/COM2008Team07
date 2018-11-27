package Controllers;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

import Models.Classification;
import Models.Grades;
import Models.GraduateType;
import Models.Module;
import Models.User;
import Models.Views;
import Views.AdminView;
import Views.LoginView;
import Views.PrimaryFrame;
import Views.StudentView;

import java.util.Collection;
import java.util.Collections;

public class AdminSystemController extends Controller{
	
	User user;
	AdminView av;
	private Views currentView;
	
	public AdminSystemController (User mainUser, AdminView aview) {
		user = mainUser;
		av = aview;
		
		initMenuView();
	}

	public void initMenuView() {
		av.loadMenuUI();
		
		av.getAccountButton().addActionListener(e -> {
			initAccountView();
		});
		
		av.getDeptartmentButton().addActionListener(e -> {
			initDepartmentView();
		});
		
		av.getDegreeButton().addActionListener(e -> {
			initDegreeView();
		});
		
		av.getModuleButton().addActionListener(e -> {
			initModuleView();
		});
	}
	
	public void initAccountView() {
		System.out.println("Switch to account view");
		av.removeUI();
		av.loadAccountUI();
		av.getBackButton().addActionListener(e ->{
			av.removeUI();
			initMenuView();
		});
	}
	
	public void initDepartmentView() {
		av.removeUI();
		av.loadDepartmentUI();
		av.getBackButton().addActionListener(e ->{
			av.removeUI();
			initMenuView();
		});
	}
	
	public void initDegreeView() {
		av.removeUI();
		av.loadDegreeUI();
		av.getBackButton().addActionListener(e ->{
			av.removeUI();
			initMenuView();
		});
	}
	
	public void initModuleView() {
		av.removeUI();
		av.loadModuleUI();
		av.getBackButton().addActionListener(e ->{
			av.removeUI();
			initMenuView();
		});
	}
	
	public void initAddAccountView() {
		
	}
	
	public void initAddDepartmentView() {
		
	}
	
	public void initAddDegreeView() {
		
	}
	
	public void initAddModuleView() {
		
	}
	// get accounts data
	// get department data
	// get degree data
	// get module data
}
