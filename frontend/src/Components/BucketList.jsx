import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTrash } from "@fortawesome/free-solid-svg-icons";
import { DeleteBucket, GetBucketList } from "../Services/BucketService";

const BucketList = () => {
  const [bucketList, setBucketList] = useState([]);
  const navigate = useNavigate();

  // Méthode pour récupérer la liste des buckets
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await GetBucketList();
        setBucketList(response.data);
        console.log(response.data);
      } catch (error) {
        console.error("Erreur lors de la requête :", error);
      }
    };

    fetchData();
  }, []);

  // Méthode pour supprimer un bucket
  const deleteBucket = async (bucket) => {
    try {
      const response = await DeleteBucket(bucket);
      alert("Bucket correctement supprimé");
      window.location.reload();
    } catch (error) {
      console.error("Erreur de suppression sur le bucket :", error);
    }
  };

  // Méthode qui redirige vers la page de modification
  const updateRedirection = (bucketId) => {
    if (bucketId) {
      console.log(bucketId);
      return navigate(`/dashboard/updateBucket/${bucketId}`);
    }
  };

  return (
    <>
      <div className="title-wrapper pt-30">
        <div className="row align-items-center">
          <div className="col-md-6">
            <div className="title">
              <h2>Listes des buckets</h2>
            </div>
          </div>
        </div>
      </div>

      <div className="row">
        {bucketList.map((bucket) => (
          <div key={bucket.id} className="col-xl-3 col-lg-4 col-sm-6">
            <div className="icon-card mb-30">
              <div className="icon purple">
                <i className="lni lni-cart-full"></i>
              </div>
              <div className="content">
                <div className="edit-delete-icons">
                  <FontAwesomeIcon
                    icon={faEdit}
                    className="edit-icon"
                    onClick={() => updateRedirection(bucket.id)}
                  />
                  <FontAwesomeIcon
                    icon={faTrash}
                    className="delete-icon"
                    onClick={() => deleteBucket(bucket.id)}
                  />
                </div>
                <h2 className="mb-10">{bucket.label}</h2>
                <h6 className="text-bold mb-10">{bucket.description}</h6>
              </div>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default BucketList;
