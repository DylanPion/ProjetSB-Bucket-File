import instance from "../api/http";

export const GetBucketList = () => {
  return instance.get("/buckets/");
};

export const GetBucketById = (bucketId) => {
  return instance.get(`/buckets/${bucketId}`);
};

export const CreateBucket = (data) => {
  return instance.post("/buckets/", data);
};

export const DeleteBucket = (bucketId) => {
  return instance.delete(`/buckets/${bucketId}`);
};

export const UpdateBucket = (data, bucketId) => {
  return instance.put(`/buckets/${bucketId}`, data);
};
