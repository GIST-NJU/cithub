import sqlite3
import json



class Db:
    def __init__(self,database:str) -> None:
        self.conn=sqlite3.connect(database)
        self.c=self.conn.cursor()
        
