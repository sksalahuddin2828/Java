using System;
using System.Collections.Generic;
using System.IO;
using Newtonsoft.Json;

class PharmacyManagementSystem
{
    private Dictionary<string, Dictionary<string, dynamic>> drugInventory = new Dictionary<string, Dictionary<string, dynamic>>();

    private void AddDrug()
    {
        Console.Write("Enter drug name: ");
        string name = Console.ReadLine();
        Console.Write("Enter price: ");
        float price = float.Parse(Console.ReadLine());
        Console.Write("Enter quantity: ");
        int quantity = int.Parse(Console.ReadLine());
        drugInventory[name] = new Dictionary<string, dynamic> { { "price", price }, { "quantity", quantity } };
        Console.WriteLine("Drug added successfully!");
    }

    private void UpdateDrug()
    {
        Console.Write("Enter drug name: ");
        string name = Console.ReadLine();
        if (drugInventory.ContainsKey(name))
        {
            Console.Write("Enter new price: ");
            float price = float.Parse(Console.ReadLine());
            Console.Write("Enter new quantity: ");
            int quantity = int.Parse(Console.ReadLine());
            drugInventory[name]["price"] = price;
            drugInventory[name]["quantity"] = quantity;
            Console.WriteLine("Drug information updated successfully!");
        }
        else
        {
            Console.WriteLine("Drug not found in the inventory!");
        }
    }

    private void ViewDrug()
    {
        Console.Write("Enter drug name (leave blank to view all drugs): ");
        string name = Console.ReadLine();
        if (!string.IsNullOrEmpty(name))
        {
            if (drugInventory.ContainsKey(name))
            {
                Dictionary<string, dynamic> drug = drugInventory[name];
                Console.WriteLine($"Drug Name: {name}");
                Console.WriteLine($"Price: {drug["price"]}");
                Console.WriteLine($"Quantity: {drug["quantity"]}");
            }
            else
            {
                Console.WriteLine("Drug not found in the inventory!");
            }
        }
        else
        {
            if (drugInventory.Count == 0)
            {
                Console.WriteLine("No drugs in the inventory!");
            }
            else
            {
                foreach (KeyValuePair<string, Dictionary<string, dynamic>> drug in drugInventory)
                {
                    Console.WriteLine($"Drug Name: {drug.Key}");
                    Console.WriteLine($"Price: {drug.Value["price"]}");
                    Console.WriteLine($"Quantity: {drug.Value["quantity"]}");
                }
            }
        }
    }

    private void RecordPurchase()
    {
        Console.Write("Enter drug name: ");
        string name = Console.ReadLine();
        if (drugInventory.ContainsKey(name))
        {
            Console.Write("Enter quantity purchased: ");
            int quantity = int.Parse(Console.ReadLine());
            if (quantity <= drugInventory[name]["quantity"])
            {
                drugInventory[name]["quantity"] -= quantity;
                Console.WriteLine("Purchase recorded successfully!");
            }
            else
            {
                Console.WriteLine("Insufficient quantity in the inventory!");
            }
        }
        else
        {
            Console.WriteLine("Drug not found in the inventory!");
        }
    }

    private void SearchDrug()
    {
        Console.Write("Enter a keyword to search for drugs: ");
        string keyword = Console.ReadLine();
        List<string> searchResults = new List<string>();
        foreach (KeyValuePair<string, Dictionary<string, dynamic>> drug in drugInventory)
        {
            if (drug.Key.ToLower().Contains(keyword.ToLower()))
            {
                searchResults.Add(drug.Key);
            }
        }
        if (searchResults.Count > 0)
        {
            Console.WriteLine("Search Results:");
            foreach (string result in searchResults)
            {
                Console.WriteLine(result);
            }
        }
        else
        {
            Console.WriteLine("No drugs found matching the keyword.");
        }
    }

    private void DeleteDrug()
    {
        Console.Write("Enterdrug name to delete: ");
        string name = Console.ReadLine();
        if (drugInventory.ContainsKey(name))
        {
            drugInventory.Remove(name);
            Console.WriteLine($"{name} deleted from the inventory.");
        }
        else
        {
            Console.WriteLine("Drug not found in the inventory!");
        }
    }

    private void SetExpirationDate()
    {
        Console.Write("Enter drug name: ");
        string name = Console.ReadLine();
        if (drugInventory.ContainsKey(name))
        {
            Console.Write("Enter expiration date (YYYY-MM-DD): ");
            string expirationDate = Console.ReadLine();
            drugInventory[name]["expiration_date"] = expirationDate;
            Console.WriteLine("Expiration date set successfully!");
        }
        else
        {
            Console.WriteLine("Drug not found in the inventory!");
        }
    }

    private void CheckLowStockAlert()
    {
        Console.Write("Enter the minimum quantity threshold: ");
        int threshold = int.Parse(Console.ReadLine());
        List<string> lowStockDrugs = new List<string>();
        foreach (KeyValuePair<string, Dictionary<string, dynamic>> drug in drugInventory)
        {
            if (drug.Value["quantity"] <= threshold)
            {
                lowStockDrugs.Add(drug.Key);
            }
        }
        if (lowStockDrugs.Count > 0)
        {
            Console.WriteLine("Low Stock Drugs:");
            foreach (string drug in lowStockDrugs)
            {
                Console.WriteLine(drug);
            }
        }
        else
        {
            Console.WriteLine("No drugs are below the quantity threshold.");
        }
    }

    private void GenerateSalesReport()
    {
        float totalSales = 0;
        foreach (KeyValuePair<string, Dictionary<string, dynamic>> drug in drugInventory)
        {
            float price = drug.Value["price"];
            int quantitySold = drug.Value["quantity"] - drugInventory[drug.Key]["quantity"];
            totalSales += price * quantitySold;
        }

        Console.WriteLine($"Total Sales: ${totalSales:F2}");

        List<KeyValuePair<string, Dictionary<string, dynamic>>> sortedDrugs = new List<KeyValuePair<string, Dictionary<string, dynamic>>>(drugInventory);
        sortedDrugs.Sort((x, y) => y.Value["quantity"].CompareTo(x.Value["quantity"]));
        Console.WriteLine("Top Selling Drugs:");
        for (int i = 0; i < Math.Min(sortedDrugs.Count, 5); i++)
        {
            KeyValuePair<string, Dictionary<string, dynamic>> drug = sortedDrugs[i];
            Console.WriteLine($"Drug Name: {drug.Key}");
            Console.WriteLine($"Quantity Sold: {drug.Value["quantity"] - drugInventory[drug.Key]["quantity"]}");
        }
    }

    private void UserAuthentication()
    {
        Console.Write("Enter username: ");
        string username = Console.ReadLine();
        Console.Write("Enter password: ");
        string password = Console.ReadLine();

        if (username == "admin" && password == "password")
        {
            Console.WriteLine("Authentication successful. Access granted.");
        }
        else
        {
            Console.WriteLine("Authentication failed. Access denied.");
        }
    }

    private void SaveData()
    {
        string json = JsonConvert.SerializeObject(drugInventory);
        File.WriteAllText("drug_inventory.json", json);
        Console.WriteLine("Data saved successfully.");
    }

    private void LoadData()
    {
        try
        {
            string json = File.ReadAllText("drug_inventory.json");
            drugInventory = JsonConvert.DeserializeObject<Dictionary<string, Dictionary<string, dynamic>>>(json);
            Console.WriteLine("Data loaded successfully.");
        }
        catch (FileNotFoundException)
        {
            Console.WriteLine("No previous data found.");
        }
    }

    public void Menu()
    {
        while (true)
        {
            Console.WriteLine("\nPharmacy Management System");
            Console.WriteLine("1. Add Drug");
            ConsoleConsole.WriteLine("2. Update Drug Information");
            Console.WriteLine("3. View Drug Information");
            Console.WriteLine("4. Record Purchase");
            Console.WriteLine("5. Search Drug");
            Console.WriteLine("6. Delete Drug");
            Console.WriteLine("7. Set Expiration Date");
            Console.WriteLine("8. Check Low Stock Alert");
            Console.WriteLine("9. Generate Sales Report");
            Console.WriteLine("10. User Authentication");
            Console.WriteLine("11. Save Data");
            Console.WriteLine("12. Load Data");
            Console.WriteLine("13. Quit");

            Console.Write("Enter your choice: ");
            string choice = Console.ReadLine();

            switch (choice)
            {
                case "1":
                    AddDrug();
                    break;
                case "2":
                    UpdateDrug();
                    break;
                case "3":
                    ViewDrug();
                    break;
                case "4":
                    RecordPurchase();
                    break;
                case "5":
                    SearchDrug();
                    break;
                case "6":
                    DeleteDrug();
                    break;
                case "7":
                    SetExpirationDate();
                    break;
                case "8":
                    CheckLowStockAlert();
                    break;
                case "9":
                    GenerateSalesReport();
                    break;
                case "10":
                    UserAuthentication();
                    break;
                case "11":
                    SaveData();
                    break;
                case "12":
                    LoadData();
                    break;
                case "13":
                    return;
                default:
                    Console.WriteLine("Invalid choice. Try again!");
                    break;
            }
        }
    }
}

class Program
{
    static void Main()
    {
        PharmacyManagementSystem pharmacyManagementSystem = new PharmacyManagementSystem();
        pharmacyManagementSystem.Menu();
    }
}
