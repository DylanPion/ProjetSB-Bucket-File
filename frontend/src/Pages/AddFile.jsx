import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { GetBucketList } from "../Services/BucketService";

const AddFile = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleFileUpload = async () => {
    if (!file) {
      setErrorMessage("Veuillez sélectionner un fichier");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post("/api/files/save", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      console.log(response.data);
    } catch (error) {
      console.error(
        "Erreur lors du téléchargement du fichier :",
        error.message
      );
      setErrorMessage(
        `Erreur lors du téléchargement du fichier : ${error.message}`
      );
    }
  };

  return (
    <>
      <h1>Liste des fichiers</h1>
      <div className="card-style mb-30">
        <div className="table-wrapper table-responsive">
          <form>
            <div className="row">
              <div className="col-12">
                <div className="input-style-1">
                  <input type="file" onChange={handleFileChange} />
                </div>
              </div>
              <div className="col-12">
                <div className="button-group d-flex justify-content-center flex-wrap">
                  <button
                    className="main-btn primary-btn btn-hover w-100 text-center"
                    onClick={handleFileUpload}
                  >
                    Envoyer le fichier
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default AddFile;
