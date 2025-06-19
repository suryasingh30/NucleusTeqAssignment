from dotenv import load_dotenv
import os

load_dotenv()

class Settings:
    PROJECT_NAME: str = os.getenv("PROJECT_NAME") or ""
    API_V1_STR: str = os.getenv("API_V1_STR") or ""
    SECRET_KEY: str = os.getenv("SECRET_KEY") or ""

settings = Settings()
