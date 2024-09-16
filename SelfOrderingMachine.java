import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class SelfOrderingMachine {
    private JFrame mainFrame;
    private JFrame cartFrame;
    private JFrame setMenuFrame;
    private JFrame singleOrderFrame;

    private JButton setMenuButton;
    private JButton singleOrderButton;
    private JButton showCartButton;
    private JButton backButton;
    private JButton checkoutButton;

    private JLabel totalPriceLabel;
    private double totalPrice;

    private String[] setMenuNames = {
        "�����ѽuü+�ܥ֬���",
        "�����ѽu�Q+���p�̿|",
        "���f�N�q�ޱƳ�+�ɦ̿@��",
        "���\�G�׵�+�ܥ֬���",
        "�A�����L��+�ɦ̿@��",
        "�A�p���׿N+�N������",
        "�N������+�ܥ֬���",
        "���f�N�q�ޱƳ�+�ަץi�ֻ�",
        "�����ѽuü+�A�����L��",
        "����ܥ֬���",
        "���f�N�q�ޱƳ�+�L������",
        "���\�G�׵�+�����A�p��"
    };

    private double[] setMenuPrices = {
        99.0, 79.0, 89.0, 99.0, 94.0, 98.0,
        102.0, 99.0, 109.0, 103.0, 109.0, 89.0
    };

    private String[] singleOrderNames = {
        "�A�����L��(�������)",
        "���f�N�q�ޱƳ�",
        "�N������(����)",
        "�ư��p����(�j)",
        "�A�p����ü",
        "�����ѽuü(������)",
        "���\�G�׵�",
        "�ɦ̿@��",
        "�L������9�J",
        "�L������5�J",
        "�����A�p��",
        "�ܥ����L",
        "�ܥ�����",
        "�q������i����",
        "���p�̿|",
        "�ަץi�ֻ�",
        "����(5��/��)",
        "�a������(���f�X��)",
        "�W������"
    };

    private double[] singleOrderPrices = {
        63.0, 56.0, 44.0, 69.0, 39.0, 36.0, 36.0, 23.0, 78.0, 47.0,
        45.0, 45.0, 45.0, 41.0, 31.0, 29.0, 29.0, 28.0, 27.0
    };

    private String[] cartItems = new String[20];
    private double[] cartPrices = new double[20];
    private int cartItemCount = 0;

    public SelfOrderingMachine() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SelfOrderingMachine selfOrderingMachine = new SelfOrderingMachine();
        selfOrderingMachine.showMainFrame();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("�����~���ۧU�I�\��");
        mainFrame.setSize(900, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 1));

        setMenuButton = new JButton("�M�\");
        setMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSetMenu();
            }
        });

        singleOrderButton = new JButton("���I");
        singleOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSingleOrder();
            }
        });

        showCartButton = new JButton("����ʪ������e");
        showCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCart();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(setMenuButton);
        buttonPanel.add(singleOrderButton);
        buttonPanel.add(showCartButton);

        mainFrame.add(buttonPanel);
    }

    private void showMainFrame() {
        mainFrame.setVisible(true);
    }

    private void showSetMenu() {
        mainFrame.setVisible(false);
        setMenuFrame = new JFrame("�M�\");
        setMenuFrame.setSize(900, 600);
        setMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMenuFrame.setLayout(new GridLayout(4, 3));
		for (int i = 0; i < setMenuNames.length; i++) {
			final String itemName = setMenuNames[i];
			final double itemPrice = setMenuPrices[i];

			JPanel menuItemPanel = new JPanel();
			menuItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			menuItemPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					showConfirmationDialog(itemName, itemPrice);
				}});
			JLabel itemNameLabel = new JLabel((i+1) + ". " + itemName);
			JLabel itemPriceLabel = new JLabel("$" + Double.toString(itemPrice));

			menuItemPanel.add(itemNameLabel);
			menuItemPanel.add(itemPriceLabel);
			setMenuFrame.add(menuItemPanel);
		}
        backButton = new JButton("�W�@��");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setMenuFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(backButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(new JLabel());
        setMenuFrame.add(buttonPanel, BorderLayout.SOUTH);
        setMenuFrame.setVisible(true);
    }

    private void showSingleOrder() {
        mainFrame.setVisible(false);
        singleOrderFrame = new JFrame("���I");
        singleOrderFrame.setSize(900, 600);
        singleOrderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        singleOrderFrame.setLayout(new GridLayout(5, 4));
        for (int i = 0; i < singleOrderNames.length; i++) {
            final String itemName = singleOrderNames[i];
            final double itemPrice = singleOrderPrices[i];
            JPanel menuItemPanel = new JPanel();
            menuItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            menuItemPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    showConfirmationDialog(itemName, itemPrice);
                }
            });
            JLabel itemNameLabel = new JLabel((i+1) + ". "+itemName);
            JLabel itemPriceLabel = new JLabel("$" + Double.toString(itemPrice));
            menuItemPanel.add(itemNameLabel);
            menuItemPanel.add(itemPriceLabel);
            singleOrderFrame.add(menuItemPanel);
        }
        backButton = new JButton("�W�@��");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                singleOrderFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(backButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(new JLabel());
        singleOrderFrame.add(buttonPanel, BorderLayout.SOUTH);
        singleOrderFrame.setVisible(true);
    }

    private void showCart() {
        cartFrame = new JFrame("�ʪ���");
        cartFrame.setSize(900, 600);
        cartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cartFrame.setLayout(new GridLayout(4, 3));
        for (int i = 0; i < cartItemCount; i++) {
            JPanel cartItemPanel = new JPanel();
            cartItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel itemNameLabel = new JLabel(cartItems[i]);
            JLabel itemPriceLabel = new JLabel("$" + Double.toString(cartPrices[i])+"��");
            cartItemPanel.add(itemNameLabel);
            cartItemPanel.add(itemPriceLabel);
            cartFrame.add(cartItemPanel);
        }
        totalPriceLabel = new JLabel("$" + Double.toString(totalPrice)+"��");
        checkoutButton = new JButton("���b");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOrderToFile();
                cartFrame.dispose();
                mainFrame.dispose();
            }
        });
        backButton = new JButton("�W�@��");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cartFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(backButton);
        buttonPanel.add(totalPriceLabel);
        buttonPanel.add(checkoutButton);
        cartFrame.add(buttonPanel, BorderLayout.SOUTH);
        cartFrame.setVisible(true);
    }

    private void showConfirmationDialog(final String itemName, final double itemPrice) {
        final JFrame confirmationFrame = new JFrame("�T�{�\�I�λ���");
        confirmationFrame.setSize(400, 300);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel confirmationPanel = new JPanel(new GridLayout(3, 1));
        JLabel itemNameLabel = new JLabel(itemName);
        JLabel itemPriceLabel = new JLabel("$" + Double.toString(itemPrice)+"��");
        JButton addToCartButton = new JButton("�[�J�ʪ���");
        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToCart(itemName, itemPrice);
                confirmationFrame.dispose();
            }
        });
        JButton cancelButton = new JButton("����");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmationFrame.dispose();
            }
        });
        confirmationPanel.add(itemNameLabel);
        confirmationPanel.add(itemPriceLabel);
        confirmationPanel.add(addToCartButton);
        confirmationPanel.add(cancelButton);

        confirmationFrame.add(confirmationPanel);
        confirmationFrame.setVisible(true);
    }

    private void addToCart(String itemName, double itemPrice) {
        cartItems[cartItemCount] = itemName;
        cartPrices[cartItemCount] = itemPrice;
        cartItemCount++;

        totalPrice += itemPrice;
        totalPriceLabel.setText("$" + Double.toString(totalPrice));
    }

    private void writeOrderToFile() {
        try {
            FileWriter writer = new FileWriter("�I�\.txt");
            for (int i = 0; i < cartItemCount; i++) {
                writer.write(cartItems[i] + " - $" + cartPrices[i] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
