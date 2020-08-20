import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClasse } from 'app/shared/model/classe.model';
import { getEntities as getClasses } from 'app/entities/classe/classe.reducer';
import { getEntity, updateEntity, createEntity, reset } from './etudiant.reducer';
import { IEtudiant } from 'app/shared/model/etudiant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEtudiantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EtudiantUpdate = (props: IEtudiantUpdateProps) => {
  const [classeId, setClasseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { etudiantEntity, classes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/etudiant');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClasses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...etudiantEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.etudiant.home.createOrEditLabel">
            <Translate contentKey="intranetApp.etudiant.home.createOrEditLabel">Create or edit a Etudiant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : etudiantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="etudiant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="etudiant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="etudiant-login">
                  <Translate contentKey="intranetApp.etudiant.login">Login</Translate>
                </Label>
                <AvField
                  id="etudiant-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mdpLabel" for="etudiant-mdp">
                  <Translate contentKey="intranetApp.etudiant.mdp">Mdp</Translate>
                </Label>
                <AvField
                  id="etudiant-mdp"
                  type="text"
                  name="mdp"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cneLabel" for="etudiant-cne">
                  <Translate contentKey="intranetApp.etudiant.cne">Cne</Translate>
                </Label>
                <AvField
                  id="etudiant-cne"
                  type="text"
                  name="cne"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="etudiant-email">
                  <Translate contentKey="intranetApp.etudiant.email">Email</Translate>
                </Label>
                <AvField
                  id="etudiant-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="etudiant-nom">
                  <Translate contentKey="intranetApp.etudiant.nom">Nom</Translate>
                </Label>
                <AvField
                  id="etudiant-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="etudiant-prenom">
                  <Translate contentKey="intranetApp.etudiant.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="etudiant-prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="telLabel" for="etudiant-tel">
                  <Translate contentKey="intranetApp.etudiant.tel">Tel</Translate>
                </Label>
                <AvField id="etudiant-tel" type="text" name="tel" validate={{}} />
              </AvGroup>
              <AvGroup>
                <Label for="etudiant-classe">
                  <Translate contentKey="intranetApp.etudiant.classe">Classe</Translate>
                </Label>
                <AvInput id="etudiant-classe" type="select" className="form-control" name="classe.id">
                  <option value="" key="0" />
                  {classes
                    ? classes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nomClasse}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/etudiant" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  classes: storeState.classe.entities,
  etudiantEntity: storeState.etudiant.entity,
  loading: storeState.etudiant.loading,
  updating: storeState.etudiant.updating,
  updateSuccess: storeState.etudiant.updateSuccess,
});

const mapDispatchToProps = {
  getClasses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EtudiantUpdate);
