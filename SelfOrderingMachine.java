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
        "赤肉麵線羹+脆皮炸雞",
        "赤肉麵線娘+香酥米糕",
        "香檸吉司豬排堡+玉米濃湯",
        "五穀瘦肉粥+脆皮炸雞",
        "鮮脆雞腿堡+玉米濃湯",
        "鮮酥雞肉燒+烤醬雞堡",
        "烤醬雞堡+脆皮炸雞",
        "香檸吉司豬排堡+豬肉可樂餅",
        "赤肉麵線羹+鮮脆雞腿堡",
        "兩塊脆皮炸雞",
        "香檸吉司豬排堡+無骨雞塊",
        "五穀瘦肉粥+黃金鮮酥雞"
    };

    private double[] setMenuPrices = {
        99.0, 79.0, 89.0, 99.0, 94.0, 98.0,
        102.0, 99.0, 109.0, 103.0, 109.0, 89.0
    };

    private String[] singleOrderNames = {
        "鮮脆雞腿堡(原味辣味)",
        "香檸吉司豬排堡",
        "烤醬雞堡(輕辣)",
        "排骨酥肉麵(大)",
        "鮮酥雞肉羹",
        "赤肉麵線羹(附辣醬)",
        "五穀瘦肉粥",
        "玉米濃湯",
        "無骨雞塊9入",
        "無骨雞塊5入",
        "黃金鮮酥雞",
        "脆皮雞腿",
        "脆皮雞塊",
        "義式紅醬波浪薯",
        "香酥米糕",
        "豬肉可樂餅",
        "麻糬棒(5支/份)",
        "地瓜薯條(附番茄醬)",
        "超長熱狗"
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
        mainFrame = new JFrame("丹丹漢堡自助點餐機");
        mainFrame.setSize(900, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 1));

        setMenuButton = new JButton("套餐");
        setMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSetMenu();
            }
        });

        singleOrderButton = new JButton("單點");
        singleOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSingleOrder();
            }
        });

        showCartButton = new JButton("顯示購物車內容");
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
        setMenuFrame = new JFrame("套餐");
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
        backButton = new JButton("上一頁");
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
        singleOrderFrame = new JFrame("單點");
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
        backButton = new JButton("上一頁");
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
        cartFrame = new JFrame("購物車");
        cartFrame.setSize(900, 600);
        cartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cartFrame.setLayout(new GridLayout(4, 3));
        for (int i = 0; i < cartItemCount; i++) {
            JPanel cartItemPanel = new JPanel();
            cartItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel itemNameLabel = new JLabel(cartItems[i]);
            JLabel itemPriceLabel = new JLabel("$" + Double.toString(cartPrices[i])+"元");
            cartItemPanel.add(itemNameLabel);
            cartItemPanel.add(itemPriceLabel);
            cartFrame.add(cartItemPanel);
        }
        totalPriceLabel = new JLabel("$" + Double.toString(totalPrice)+"元");
        checkoutButton = new JButton("結帳");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeOrderToFile();
                cartFrame.dispose();
                mainFrame.dispose();
            }
        });
        backButton = new JButton("上一頁");
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
        final JFrame confirmationFrame = new JFrame("確認餐點及價格");
        confirmationFrame.setSize(400, 300);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel confirmationPanel = new JPanel(new GridLayout(3, 1));
        JLabel itemNameLabel = new JLabel(itemName);
        JLabel itemPriceLabel = new JLabel("$" + Double.toString(itemPrice)+"元");
        JButton addToCartButton = new JButton("加入購物車");
        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToCart(itemName, itemPrice);
                confirmationFrame.dispose();
            }
        });
        JButton cancelButton = new JButton("取消");
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
            FileWriter writer = new FileWriter("點餐.txt");
            for (int i = 0; i < cartItemCount; i++) {
                writer.write(cartItems[i] + " - $" + cartPrices[i] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
