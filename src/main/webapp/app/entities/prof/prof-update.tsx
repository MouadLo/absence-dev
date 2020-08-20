import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMatiere } from 'app/shared/model/matiere.model';
import { getEntities as getMatieres } from 'app/entities/matiere/matiere.reducer';
import { getEntity, updateEntity, createEntity, reset } from './prof.reducer';
import { IProf } from 'app/shared/model/prof.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfUpdate = (props: IProfUpdateProps) => {
  const [matiereId, setMatiereId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { profEntity, matieres, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/prof');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMatieres();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...profEntity,
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
          <h2 id="intranetApp.prof.home.createOrEditLabel">
            <Translate contentKey="intranetApp.prof.home.createOrEditLabel">Create or edit a Prof</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : profEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="prof-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="prof-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="prof-login">
                  <Translate contentKey="intranetApp.prof.login">Login</Translate>
                </Label>
                <AvField
                  id="prof-login"
                  type="text"
                  name="login"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mdpLabel" for="prof-mdp">
                  <Translate contentKey="intranetApp.prof.mdp">Mdp</Translate>
                </Label>
                <AvField
                  id="prof-mdp"
                  type="text"
                  name="mdp"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="prof-email">
                  <Translate contentKey="intranetApp.prof.email">Email</Translate>
                </Label>
                <AvField
                  id="prof-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="prof-nom">
                  <Translate contentKey="intranetApp.prof.nom">Nom</Translate>
                </Label>
                <AvField
                  id="prof-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="prof-prenom">
                  <Translate contentKey="intranetApp.prof.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="prof-prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="prof-type">
                  <Translate contentKey="intranetApp.prof.type">Type</Translate>
                </Label>
                <AvField
                  id="prof-type"
                  type="text"
                  name="type"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="telLabel" for="prof-tel">
                  <Translate contentKey="intranetApp.prof.tel">Tel</Translate>
                </Label>
                <AvField
                  id="prof-tel"
                  type="text"
                  name="tel"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/prof" replace color="info">
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
  matieres: storeState.matiere.entities,
  profEntity: storeState.prof.entity,
  loading: storeState.prof.loading,
  updating: storeState.prof.updating,
  updateSuccess: storeState.prof.updateSuccess,
});

const mapDispatchToProps = {
  getMatieres,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfUpdate);
