import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { CreateAccount } from "../Services/UserServices";

const Signup = () => {
  const {
    register, // enregistrer les champs de saisie du formulaire
    handleSubmit, // gère la soumission
    formState: { errors }, // conetient les état du formulaire avec les erreurs
  } = useForm();

  const navigate = useNavigate();

  const onSubmit = async (data) => {
    console.log(data);
    const response = await CreateAccount(data);
    alert(`Votre compte  a été créé avec succès`);
    setTimeout(() => {
      navigate("/dashboard");
    }, 500);
  };

  return (
    <main className="main-wrapper">
      <section className="signin-section">
        <div className="container-fluid">
          <div className="title-wrapper pt-30">
            <div className="row align-items-center">
              <div className="col-md-6"></div>
            </div>
          </div>
          <div className="row g-0 auth-row">
            <div className="col-lg-6">
              <div className="auth-cover-wrapper bg-primary-100">
                <div className="auth-cover">
                  <div className="title text-center">
                    <h1 className="text-primary mb-10">
                      Bienvenue sur l'application
                    </h1>
                    <p className="text-medium">
                      Inscrivez vous pour pouvoir continuer !
                    </p>
                  </div>
                  <div className="cover-image">
                    <img src="assets/images/auth/signin-image.svg" alt="" />
                  </div>
                  <div className="shape-image">
                    <img src="assets/images/auth/shape.svg" alt="" />
                  </div>
                </div>
              </div>
            </div>
            <div className="col-lg-6">
              <div className="signin-wrapper">
                <div className="form-wrapper">
                  <h6 className="mb-15">Formulaire d'inscription</h6>
                  <p className="mb-10">
                    Commencer à utliser l'application dès votre inscription.
                  </p>
                  <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="row">
                      <div className="col-12">
                        <div className="input-style-1">
                          <label>Prénom</label>
                          <input
                            defaultValue=""
                            {...register("firstName", { required: true })}
                          />
                          {errors.login && <span>Ce champ est requis</span>}
                        </div>
                      </div>
                      <div className="col-12">
                        <div className="input-style-1">
                          <label>Nom</label>
                          <input
                            defaultValue=""
                            {...register("lastName", { required: true })}
                          />
                          {errors.login && <span>Ce champ est requis</span>}
                        </div>
                      </div>
                      <div className="col-12">
                        <div className="input-style-1">
                          <label>Adresse e-mail</label>
                          <input
                            defaultValue=""
                            {...register("login", { required: true })}
                          />
                          {errors.login && <span>Ce champ est requis</span>}
                        </div>
                      </div>
                      <div className="col-12">
                        <div className="input-style-1">
                          <label>Mot de passe</label>
                          <input
                            type="password"
                            defaultValue=""
                            {...register("password", { required: true })}
                          />
                          {errors.password && <span>Ce champ est requis</span>}
                        </div>
                      </div>
                      <div className="col-12">
                        <div className="button-group d-flex justify-content-center flex-wrap">
                          <button
                            type="submit"
                            className="main-btn primary-btn btn-hover w-100 text-center"
                          >
                            Inscription
                          </button>
                        </div>
                      </div>
                    </div>
                  </form>
                  <div className="singin-option pt-40">
                    <p className="text-sm text-medium text-dark text-center">
                      Avez-vous un compte ?{" "}
                      <Link to={`/login`}>Connectez-vous</Link>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default Signup;
