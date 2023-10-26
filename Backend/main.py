from typing import Union

from fastapi import FastAPI
from fastapi.responses import JSONResponse

import json
from db import Db






app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None, stx : Union[str,None]=None):
    return {"msg":"success","item_id": item_id, "q": q, "stx":stx}

@app.get("/userid/{user_id}")
def get_user(user_id:int):
    db=Db("test.db")
    cursor=db.c.execute(f"SELECT * from COMPANY where id={user_id}")
    person={"id":"","name":"","addr":"","salary":""}
    for row in cursor:
       person["id"]=row[0]
       person["name"]=row[1]
       person["addr"]=row[2]
       person["salary"]=row[3]
    db.conn.close()
    return person
    
