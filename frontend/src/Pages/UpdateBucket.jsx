import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  GetBucketById,
  UpdateBucket as UpdateBucketService,
} from "../Services/BucketService";

const UpdateBucket = () => {
  const { bucketId } = useParams();
  const [bucket, setBucket] = useState({
    label: "",
    description: "",
  });

  const {
    handleSubmit,
    register,
    setValue,
    formState: { errors },
  } = useForm();
  const navigate = useNavigate();

  const fetchData = async (bucketId) => {
    try {
      const response = await GetBucketById(bucketId);
      setBucket(response.data);
    } catch (error) {
      console.error("Erreur lors de la requête :", error);
    }
  };

  useEffect(() => {
    fetchData(bucketId);
  }, [bucketId]);

  const onSubmit = async (data) => {
    const response = await UpdateBucketService(data, bucketId);
    setTimeout(() => {
      navigate("/dashboard");
    }, 500);
  };

  useEffect(() => {
    setValue("label", bucket.label);
    setValue("description", bucket.description);
  }, [bucket, setValue]);

  return (
    <>
      <div className="title-wrapper pt-30">
        <div className="row align-items-center">
          <div className="col-md-6">
            <div className="title">
              <h2>Modifier le bucket</h2>
            </div>
          </div>
        </div>
      </div>
      <div className="col-lg-12">
        <div className="menu-toggle-btn mr-15 mb-15">
          <Link to={`/dashboard`} className="main-btn primary-btn btn-hover">
            Retour à la liste des Buckets
          </Link>
        </div>
        <div className="card-style mb-30">
          <div className="table-wrapper table-responsive">
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="row">
                <div className="col-12">
                  <div className="input-style-1">
                    <label>Label</label>
                    <input
                      type="text"
                      {...register("label", {
                        required: "Ce champ est requis",
                      })}
                    />
                    {errors.label && <span>{errors.label.message}</span>}
                  </div>
                </div>
                <div className="col-12">
                  <div className="input-style-1">
                    <label>Description</label>
                    <textarea
                      {...register("description", {
                        required: "Ce champ est requis",
                      })}
                    />
                    {errors.description && (
                      <span>{errors.description.message}</span>
                    )}
                  </div>
                </div>
                <div className="col-12">
                  <div className="button-group d-flex justify-content-center flex-wrap">
                    <button
                      type="submit"
                      className="main-btn primary-btn btn-hover w-100 text-center"
                    >
                      Enregistrer
                    </button>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default UpdateBucket;
