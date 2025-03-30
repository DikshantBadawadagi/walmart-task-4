import sqlite3
import pandas as pd
import os

db_path = "new_shipment_database.db"
if os.path.exists(db_path):
    os.remove(db_path) 

conn = sqlite3.connect(db_path)
cursor = conn.cursor()

cursor.execute("""
    CREATE TABLE shipments (
        shipment_identifier TEXT,
        product TEXT,
        on_time INTEGER,
        origin_warehouse TEXT,
        destination_store TEXT,
        driver_identifier TEXT
    )
""")

cursor.execute("""
    CREATE TABLE shipments_0 (
        origin_warehouse TEXT,
        destination_store TEXT,
        product TEXT,
        on_time INTEGER,
        product_quantity INTEGER,
        driver_identifier TEXT
    )
""")

conn.commit()

data_folder = "data"
shipping_0 = pd.read_csv(os.path.join(data_folder, "shipping_data_0.csv"))
shipping_1 = pd.read_csv(os.path.join(data_folder, "shipping_data_1.csv"))
shipping_2 = pd.read_csv(os.path.join(data_folder, "shipping_data_2.csv"))

print("Data loaded successfully!")

print("Inserting shipping_data_0 into shipments_0 table...")
shipping_0.to_sql("shipments_0", conn, if_exists="append", index=False)
print("shipping_data_0 inserted successfully!")

print("Merging shipping_data_1 and shipping_data_2...")
merged_data = shipping_1.merge(shipping_2, on="shipment_identifier")
print("Merge successful!")

print("Inserting merged data into shipments table...")
for _, row in merged_data.iterrows():
    cursor.execute("""
        INSERT INTO shipments (shipment_identifier, product, on_time, origin_warehouse, destination_store, driver_identifier) 
        VALUES (?, ?, ?, ?, ?, ?)
    """, (row["shipment_identifier"], row["product"], row["on_time"], 
          row["origin_warehouse"], row["destination_store"], row["driver_identifier"]))

conn.commit()
conn.close()

print("All data successfully inserted into new_shipment_database.db!")
