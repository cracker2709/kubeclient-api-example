from pydantic import BaseModel, Field


class PodInfos(BaseModel):
    """Base model for note creation and updates"""
    name: str = Field(..., min_length=3, max_length=255, description="Pod's name")
    image: str = Field(..., min_length=3, max_length=255, description="Image name")
    version: str = Field(..., min_length=3, max_length=255, description="Version of the image")


class ErrorResponse(BaseModel):
    """Standard error response schema"""
    detail: str = Field(..., description="Error message")