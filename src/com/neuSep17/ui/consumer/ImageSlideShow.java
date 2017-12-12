package com.neuSep17.ui.consumer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.InventoryServiceAPI_Test;

public class ImageSlideShow extends JPanel implements ActionListener {
	 private List<URL> urls = new ArrayList<>();
	private ArrayList<Image> myimages = new ArrayList<>();
	Vehicle vehicle;
	private String[] buttonNames = { "first", "pre", "next", "last" };
	private int currentImage = 0;
	private int currIndex = 0;
	private int width = 600, height = 550;
	private JPanel buttonPanel = new JPanel();
	

	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbs = new GridBagConstraints();

	Map<JButton, String> buttons = new LinkedHashMap<>();
	JLabel imageLabel;
	JLabel title;
	Map<Integer, ImageIcon> images = new HashMap<>();

	public ImageSlideShow(Vehicle vehicle, ArrayList<Image> myimages) {
		this.vehicle = vehicle;
		createComponents(vehicle, myimages);
		addComponents();
		this.setVisible(true);
	}

	private void addComponents() {
		this.setLayout(gbl);
		title.setHorizontalAlignment(JLabel.LEFT);
		this.add(title);
		this.add(imageLabel);
		for (JButton jButton : buttons.keySet()) {
			buttonPanel.add(jButton);
		}
		this.add(buttonPanel);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 30, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 0;
		gbl.setConstraints(title, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 30, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 1;
		gbl.setConstraints(imageLabel, gbs);

		gbs.fill = GridBagConstraints.BOTH;
		gbs.gridwidth = 1;
		gbs.gridheight = 1;
		gbs.insets = new Insets(0, 0, 0, 0);
		gbs.weightx = 1;
		gbs.weighty = 1;
		gbs.gridx = 0;
		gbs.gridy = 2;
		gbl.setConstraints(buttonPanel, gbs);
	}

	private void createComponents(Vehicle vehicle, ArrayList<Image> myimages) {
		
		title = new JLabel();
		imageLabel = new JLabel();

		for (String buttonName : buttonNames) {
			JButton button = new JButton(buttonName);
			button.addActionListener(this);
			buttons.put(button, buttonName);
		}

//		for (String spec : imageUrls) {
////			if (isImage(spec)) {
////
////			} else {
////				spec = null;
////			}
//			if(!isImage(spec)){
//				spec = null;
//			}
//			try {
//				urls.add(new URL(spec));
//			} catch (MalformedURLException e) {
//				urls.add(null);
//			}
//		}

		loadImage(myimages);
        title.setText(vehicle.getCategory().toString());
        title.setForeground(Color.RED);
//		title.setText(vehicle.getCategory() + " " + vehicle.getYear() + " " + vehicle.getMake() + " "
//				+ vehicle.getModel() + " " + vehicle.getTrim());
		String s=" " + vehicle.getYear() + " " + vehicle.getMake() + " "
				+ vehicle.getModel() + " " + vehicle.getTrim();
		title.setText(String.format("<html>%s<font color='black'>%s</font></html>", 
		        title.getText(),s));
		title.setFont(new Font("Menu.font", Font.PLAIN, 20));
		
		
	}

	private void loadImage(ArrayList<Image> myimages) {
		// URL url = urls.get(currentImage);
		//ImageIcon icon;
		BufferedImage img = null;
//		if (url != null) {
////			icon = new ImageIcon(urls.get(currentImage));
//			icon = new ImageIcon(url);
//		} else {
//
//			try {
//				img = ImageIO.read(new File("src/com/neuSep17/ui/consumer/imagenotfound.jpg"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			icon = new ImageIcon(img);
//
//		}
		// ImageIcon icon=new ImageIcon(urls.get(currentImage));
//		System.out.println("Size = " + myimages.size() + "   currIndex =" + currIndex);
		ImageIcon icon=new ImageIcon(myimages.get(currIndex));
		icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

		if (!images.containsKey(currIndex)) {
			images.put(currIndex, icon);
		}
		imageLabel.setIcon(images.get(currIndex));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = buttons.get(e.getSource());
		if (name.equals("next")) {
			// this.currIndex = (this.currIndex + 1) % this.urls.size();
			this.currIndex = (this.currIndex + 1) % 3;
			try {
				myimages = InventoryServiceAPI_Test.getVehicleImage(vehicle.getBodyType());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (name.equals("pre")) {
			// this.currIndex = (this.currIndex - 1 + this.urls.size()) % this.urls.size();
			this.currIndex = (this.currIndex + 2) % 3;
			try {
				myimages = InventoryServiceAPI_Test.getVehicleImage(vehicle.getBodyType());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (name.equals("first")) {
			this.currIndex = 0;
			try {
				myimages = InventoryServiceAPI_Test.getVehicleImage(vehicle.getBodyType());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (name.equals("last")) {
			this.currIndex = 2;
			try {
				myimages = InventoryServiceAPI_Test.getVehicleImage(vehicle.getBodyType());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		loadImage(myimages);
	}

	private boolean isImage(String image_path) {
		Image image=null;
		try {
			image = ImageIO.read(new URL(image_path));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		if(image != null){
		    return true;
		}else{
		    return false;
		}
	}
}
