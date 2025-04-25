import pandas as pd

def filter_and_generate_excel(file1_path, file2_path, result_file_path):
    try:
        # Load the data from File1.xlsx and File2.xlsx
        df1 = pd.read_excel(file1_path, engine='openpyxl')
        df2 = pd.read_excel(file2_path, engine='openpyxl')

        # Merge the dataframes on EmployeeId using an inner join to handle large datasets efficiently
        merged_df = pd.merge(df1, df2, on='EmployeeId', how='inner')

        # Select the required columns
        result_df = merged_df[['EmployeeId', 'EmployeeName', 'ManagerId']]

        # Save the result to an Excel file
        result_df.to_excel(result_file_path, index=False)

        print(f"Filtered results have been saved to {result_file_path} successfully.")
    except FileNotFoundError as e:
        print(f"Error: {e}. Please ensure that the files exist at the specified location.")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

# Define file paths
file1_path = 'C:\\PYTHON\\File1.xlsx'
file2_path = 'C:\\PYTHON\\File2.xlsx'
result_file_path = 'C:\\PYTHON\\results.xlsx'

# Call the function to filter and generate the Excel file
filter_and_generate_excel(file1_path, file2_path, result_file_path)
