import React, { useEffect, useState } from "react";
import { Link, Outlet } from "react-router-dom";
import BucketList from "../Components/BucketList";
import { GetBucketList } from "../Services/BucketService";

const DashBoard = () => {
  const [buckets, setBuckets] = useState([]);
  useEffect(() => {
    const fetchBucket = async () => {
      try {
        const response = await GetBucketList();
        setBuckets(response.data);
      } catch (error) {
        console.log("Error fetching buckets", error);
      }
    };
    fetchBucket();
  }, []);

  return (
    <>
      <BucketList />
    </>
  );
};

export default DashBoard;
